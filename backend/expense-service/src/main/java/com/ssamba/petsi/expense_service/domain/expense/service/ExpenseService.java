package com.ssamba.petsi.expense_service.domain.expense.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssamba.petsi.expense_service.domain.expense.dto.request.*;
import com.ssamba.petsi.expense_service.domain.expense.dto.response.*;
import com.ssamba.petsi.expense_service.domain.expense.entity.MedicalExpense;
import com.ssamba.petsi.expense_service.domain.expense.entity.Purchase;
import com.ssamba.petsi.expense_service.domain.expense.repository.MedicalExpenseRepository;
import com.ssamba.petsi.expense_service.domain.expense.repository.PurchaseRepository;
import com.ssamba.petsi.expense_service.global.client.PetClient;
import com.ssamba.petsi.expense_service.global.client.UserClient;
import com.ssamba.petsi.expense_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.expense_service.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private static final long EXPENSE_PER_PAGE = 20;
    private static final String CACHE_KEY_PREFIX = "expense:";
    // python AI 카테고리 예측 요청 URI
    private static final String AI_PREDICT_URL = "http://ai-service:9008/api/v1/predict_categories";
    private final RestTemplate restTemplate = new RestTemplate();
    private final S3Service s3Service;
    private final UserClient userClient;
    private final PetClient petClient;
    private final PurchaseRepository purchaseRepository;
    private final MedicalExpenseRepository medicalExpenseRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final AsyncTaskExecutor taskExecutor;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveExpenseMenual(Long userId, PurchaseMenualPostRequestDto purchaseDto, MultipartFile file) {
        redisTemplate.delete(CACHE_KEY_PREFIX + userId);
        if (file != null) {
            // 사진 확장자인지 확인
            if (!isValidImageMimeType(file)) {
                throw new BusinessLogicException(ExceptionCode.INVALID_FILE_FORM);
            }

            // 사진 업로드
            try {
                // 파일명 지정 (겹치면 안되고, 확장자 빼먹지 않도록 조심!)
                String fileName = UUID.randomUUID() + file.getOriginalFilename();

                // 파일데이터와 파일명 넘겨서 S3에 저장
                String url = s3Service.upload(file, fileName);

                // 구매 목록 저장
                Purchase purchase = purchaseDto.convertToPurchase(userId, url);
                purchaseRepository.save(purchase);
                return;
            } catch (Exception e) {
                // 사진 업로드 오류 처리
                throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
            }
        }

        Purchase purchase = purchaseDto.convertToPurchase(userId);
        purchaseRepository.save(purchase);
    }

    @Transactional
    public void saveExpenseAi(Long userId, MarketLoginDto marketLoginDto) {
        CompletableFuture<List<Purchase>> future = CompletableFuture.supplyAsync(() -> {
            try {
                // AI 예측 API 호출
                ResponseEntity<List<PurchaseAiSaveDto>> response = restTemplate.exchange(
                        AI_PREDICT_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(marketLoginDto),
                        new ParameterizedTypeReference<List<PurchaseAiSaveDto>>() {}
                );
                List<PurchaseAiSaveDto> data = response.getBody();

                if (data != null) {
                    // Purchase 엔티티 생성 및 저장
                    List<Purchase> purchases = data.stream()
                            .map(p -> new Purchase(userId, p))
                            .toList();

                    return purchaseRepository.saveAll(purchases);
                }
                return new ArrayList<>();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, taskExecutor);

        try {
            // 여기서 결과를 얻을 때까지 기다립니다 (최대 aiTimeout 밀리초)
            future.get(300000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new RuntimeException("AI 처리 시간이 초과되었습니다.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("작업이 중단되었습니다.", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("AI 처리 중 오류가 발생했습니다.", e.getCause());
        }
    }

    @Transactional
    public void saveExpenseMedicalExpense(Long userId, MedicalExpensePostRequestDto medicalExpenseDto) {
        MedicalExpense medicalExpense = medicalExpenseDto.convertToMedicalExpense(userId);
        medicalExpenseRepository.save(medicalExpense);
    }

    @Transactional(readOnly = true)
    public Map<LocalDate, List<Expense>> getExpenses(Long userId, int page, LocalDate startDate, LocalDate endDate) {
        redisTemplate.delete(CACHE_KEY_PREFIX + userId);
        String cacheKey = CACHE_KEY_PREFIX + userId;

        if (!redisTemplate.hasKey(cacheKey)) {
            cacheExpenses(userId, startDate, endDate, cacheKey);
        }

        return paginateExpenses(cacheKey, page);
    }

    @Transactional(readOnly = true)
    public PurchaseResponseDto getPurchase(Long userId, Long purchaseId) {
        return PurchaseResponseDto.fromEntity(findPurchase(userId, purchaseId));
    }

    @Transactional(readOnly = true)
    public MedicalExpenseResponseDto getMedicalExpense(Long userId, Long medicalExpenseId) {
        MedicalExpense medicalExpense =  findMedicalExpense(userId, medicalExpenseId);
        PetDto petDto = petClient.findPetInfo(medicalExpense.getPetId());
        return MedicalExpenseResponseDto.fromEntity(medicalExpense, petDto.getPetName());
    }

    public ChartResponseDto getMonthlyExpense(Long userId, LocalDate startDate, LocalDate endDate) {
        UserDto userDto = userClient.getUser(userId);
        List<PurchaseSumDto> purchases = purchaseRepository.getSumByCategoryBetween(userId, startDate, endDate);
        long medicalExpense = medicalExpenseRepository.sumCostByBetween(startDate, endDate);
        purchases.add(new PurchaseSumDto("의료비", medicalExpense));

        long total = medicalExpense;
        for(PurchaseSumDto p : purchases) {
            total += p.getData();
        }

        Collections.sort(purchases);

        List<String> labels = new ArrayList<>();
        List<Long> datas = new ArrayList<>();
        List<Long> rates = new ArrayList<>();

        for(PurchaseSumDto p : purchases) {
            if(p.getData() == 0) continue;

            double data = ((double) p.getData() / (double) total) * 100;
            long rate = (long) Math.floor(data);

            labels.add(p.getLabels());
            datas.add(p.getData());
            rates.add(rate);
        }

        return new ChartResponseDto(userDto.getNickname(), userDto.getImg(), startDate, endDate, total, new ChartResponseDto.ChartElement(labels, datas, rates));
    }

    @Transactional
    public void updatePurchase(Long userId, PurchaseUpdateDto purchaseUpdateDto) {
        Purchase purchase = findPurchase(userId, purchaseUpdateDto.getPurchaseId());
        purchase.updateEntity(purchaseUpdateDto);
    }

    @Transactional
    public void updateMedicalExpense(Long userId, MedicalExpenseUpdateDto medicalExpenseUpdateDto) {
        MedicalExpense medicalExpense = findMedicalExpense(userId, medicalExpenseUpdateDto.getMedicalExpenseId());
        medicalExpense.updateEntity(medicalExpenseUpdateDto);
    }

    @Transactional
    public void deletePurchase(Long userId, long purchaseId) {
        purchaseRepository.deleteByUserIdAndPurchaseId(userId, purchaseId);
    }

    @Transactional
    public void deleteMedicalExpense(Long userId, long medicalExpenseId) {
        medicalExpenseRepository.deleteByUserIdAndMedicalExpenseId(userId, medicalExpenseId);
    }

    @Transactional
    public void deleteExpenses(Long userId, ExpenseDeleteRequestDto expenses) {
        purchaseRepository.deleteByUserIdAndPurchaseIdIn(userId, expenses.getPurchases());
        medicalExpenseRepository.deleteByUserIdAndMedicalExpenseIdIn(userId, expenses.getMedicalExpenses());
    }

    // 구매 내역 검증 및 조회
    private Purchase findPurchase(Long userId, Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PURCHASE_NOT_FOUND));

        if (purchase.getUserId() != userId)
            throw new BusinessLogicException(ExceptionCode.PURCHASE_USER_NOT_MATCH);

        return purchase;
    }

    // 의료비 내역 검증 및 조회
    private MedicalExpense findMedicalExpense(Long userId, Long medicalExpenseId) {
        MedicalExpense medicalExpense = medicalExpenseRepository.findById(medicalExpenseId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEDICAL_EXPENSE_NOT_FOUND));

        if (medicalExpense.getUserId() != userId)
            throw new BusinessLogicException(ExceptionCode.MEDICAL_EXPENSE_USER_NOT_MATCH);

        return medicalExpense;
    }

    // 소비 목록 캐싱
    private void cacheExpenses(Long userId, LocalDate startDate, LocalDate endDate, String cacheKey) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

        List<PurchaseResponseDto> purchases = purchaseRepository.findByUserIdAndPurchasedAtBetween(userId, startDate, endDate).stream()
                .map(PurchaseResponseDto::fromEntity)
                .toList();

        List<MedicalExpenseResponseDto> medicalExpenses = medicalExpenseRepository.findByUserIdAndVisitedAtBetween(userId, startDate, endDate).stream()
                .map(MedicalExpenseResponseDto::fromEntity)
                .toList();


        for (PurchaseResponseDto p : purchases) {
            try {
                String jsonExpense = objectMapper.writeValueAsString(p);
                zSetOps.add(cacheKey, jsonExpense, p.getPurchasedAt().toEpochDay());
            } catch (Exception ex) {
                throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
            }
        }

        for (MedicalExpenseResponseDto e : medicalExpenses) {
            try {
                String jsonExpense = objectMapper.writeValueAsString(e);
                zSetOps.add(cacheKey, jsonExpense, e.getVisitedAt().toEpochDay());
            } catch (Exception ex) {
                throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
            }
        }

        redisTemplate.expire(cacheKey, 1, TimeUnit.HOURS); // 1시간 후 캐시 만료
    }

    // 소비 페이지네이션 응답 만들기
    private Map<LocalDate, List<Expense>> paginateExpenses(String cacheKey, int page) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

        long start = page * EXPENSE_PER_PAGE;
        long end = (page + 1) * EXPENSE_PER_PAGE - 1;

        Set<String> pageItems = zSetOps.reverseRange(cacheKey, start, end);

        Map<LocalDate, List<Expense>> pageExpenses = new TreeMap<>(Comparator.reverseOrder());
        if (pageItems != null) {
            for (String jsonExpense : pageItems) {
                try {
                    JsonNode node = objectMapper.readTree(jsonExpense);
                    String type = node.get("kind").asText();
                    Expense expense;
                    if ("구매목록".equals(type)) {
                        expense = objectMapper.treeToValue(node, PurchaseResponseDto.class);
                    } else if ("의료비".equals(type)) {
                        expense = objectMapper.treeToValue(node, MedicalExpenseResponseDto.class);
                    } else {
                        throw new IllegalArgumentException("Unknown expense type: " + type);
                    }
                    LocalDate date = expense.getDate();
                    pageExpenses.computeIfAbsent(date, k -> new ArrayList<>()).add(expense);
                } catch (Exception e) {
                    // 로깅 처리
                    throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
                }
            }
        }

        return pageExpenses;
    }

    // 확장자 확인 메서드
    private boolean isValidImageMimeType(MultipartFile file) {
        // 허용되는 MIME 타입 목록
        String[] validMimeTypes = {"image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp", "image/tiff"};

        // MIME 타입 확인
        String mimeType = file.getContentType();

        // MIME 타입이 허용된 목록에 있는지 확인
        return mimeType != null && Arrays.asList(validMimeTypes).contains(mimeType);
    }
}
