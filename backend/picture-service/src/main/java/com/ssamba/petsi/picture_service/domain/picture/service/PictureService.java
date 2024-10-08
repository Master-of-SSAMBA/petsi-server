package com.ssamba.petsi.picture_service.domain.picture.service;

import com.ssamba.petsi.picture_service.domain.picture.dto.response.DateResponseDto;
import com.ssamba.petsi.picture_service.domain.picture.dto.response.PictureResponseDto;
import com.ssamba.petsi.picture_service.domain.picture.dto.response.PredictResponse;
import com.ssamba.petsi.picture_service.domain.picture.entity.Picture;
import com.ssamba.petsi.picture_service.domain.picture.repository.PictureRepository;
import com.ssamba.petsi.picture_service.global.dto.NoticeProducerDto;
import com.ssamba.petsi.picture_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.picture_service.global.exception.ExceptionCode;
import com.ssamba.petsi.picture_service.global.kafka.KafkaProducer;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PictureService {

    public static final int PICTURES_PER_PAGE = 20;
    public static final String REDIS_KEY_PREFIX = "photo_auth:";
    public static final String IS_PET_PREDICT_URL = "http://ai2-service:9009/api/v1/predict-is-pet";
    private final RedisTemplate<String, String> redisTemplate;
    private final PictureRepository pictureRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final S3Service s3Service;
    private final KafkaProducer kafkaProducer;

    @Transactional(readOnly = true)
    public List<PictureResponseDto.Response> getPictures(long userId, int page) {
        // 페이지네이션
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(new Sort.Order(Sort.Direction.DESC, "createdAt"));
        Pageable pageable = PageRequest.of(page, PICTURES_PER_PAGE, Sort.by(sorts));

        Page<Picture> list = pictureRepository.findAllByUserId(userId, pageable);

        return list.getContent().stream()
                .map(picture -> new PictureResponseDto.Response(picture.getPictureId(), picture.getImg()))
                .toList();
    }

    @Transactional
    public void savePicture(long userId, MultipartFile file, String content) {

        // 반려동물이 사진에 있는지 확인
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<PredictResponse> response = restTemplate.exchange(
                IS_PET_PREDICT_URL,
                HttpMethod.POST,
                requestEntity,
                PredictResponse.class
        );

        if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            if (!response.getBody().isPet()) {
                throw new BusinessLogicException(ExceptionCode.PET_NOT_EXIST);
            }
        }

        // 사진 저장 로직
        // 사진 확장자인지 확인
        if(!isValidImageMimeType(file)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_FILE_FORM);
        }

        // redis에 존재하는지 확인
        if (!canAuthenticate(userId)) {
            throw new BusinessLogicException(ExceptionCode.PICTURE_ALREADY_EXIST);
        }

        // 사진 업로드
        try {
            // 파일명 지정 (겹치면 안되고, 확장자 빼먹지 않도록 조심!)
            String fileName = UUID.randomUUID() + file.getOriginalFilename();
            // 파일데이터와 파일명 넘겨서 S3에 저장
            String url = s3Service.upload(file, fileName);

            Picture picture = Picture.builder()
                    .userId(userId)
                    .img(url)
                    .content(content)
                    .build();

            pictureRepository.save(picture);
            recordAuthentication(userId);
            sendNotification(userId);
        } catch (Exception e) {
            // 사진 업로드 오류 처리
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    //알림보내는 로직
    private void sendNotification(long userId) {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        Long pictureCnt = (long)getMonthlyPictures(year, month, userId).size();
        kafkaProducer.send(NoticeProducerDto.toNoticeProducerDto(pictureCnt, userId));
    }

    @Transactional(readOnly = true)
    public PictureResponseDto.Count getCount(Long userId) {
        long count = pictureRepository.countByUserId(userId);
        return new PictureResponseDto.Count(count);
    }

    @Transactional(readOnly = true)
    public PictureResponseDto.Detail getPicture(Long userId, Long pictureId) {
        Picture findPicture = pictureRepository.findById(pictureId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PICTURE_NOT_FOUND));

        // 사진의 유저 아이디와 현재 유저 아이디가 다를 때 예외 처리
        if(findPicture.getUserId() != userId) {
            throw new BusinessLogicException(ExceptionCode.PICTURE_USER_NOT_MATCH);
        }

        LocalDateTime createdAt = findPicture.getCreatedAt();
        DateResponseDto dateDto = formatDateTime(createdAt);

        return new PictureResponseDto.Detail(findPicture.getPictureId(), findPicture.getImg(), findPicture.getContent(), dateDto);
    }

    @Transactional(readOnly = true)
    public List<Integer> getMonthlyPictures(int year, int month, Long userId) {
        List<Integer> list = pictureRepository.findByUserIdAndYearAndMonth(userId, year, month).stream()
                .map(p -> p.getCreatedAt().getDayOfMonth())
                .toList();
        return list;
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

    // redis 키 생성
    private String getRedisKey(Long userId) {
        return REDIS_KEY_PREFIX + userId + ":" + LocalDate.now();
    }

    // redis에 인증 여부 존재 확인
    private boolean canAuthenticate(Long userId) {
        String key = getRedisKey(userId);
        Boolean keyExists = redisTemplate.hasKey(key);
        return keyExists == null || !keyExists;
    }

    // redis에 인증 여부 등록
    private void recordAuthentication(Long userId) {
        String key = getRedisKey(userId);
        long secondsUntilMidnight = getSecondsUntilMidnight();
        redisTemplate.opsForValue().set(key, "1", secondsUntilMidnight, TimeUnit.SECONDS);
    }

    // 자정까지 얼마나 남았는지 계산
    private long getSecondsUntilMidnight() {
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        LocalTime midnight = LocalTime.MIDNIGHT;
        long secondsUntilMidnight = now.until(midnight, java.time.temporal.ChronoUnit.SECONDS);
        return secondsUntilMidnight > 0 ? secondsUntilMidnight : 24 * 60 * 60; // 24 hours in seconds
    }

    // 날짜 파싱 메서드
    public static DateResponseDto formatDateTime(LocalDateTime dateTime) {
        String date = dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return new DateResponseDto(date, time);
    }


}
