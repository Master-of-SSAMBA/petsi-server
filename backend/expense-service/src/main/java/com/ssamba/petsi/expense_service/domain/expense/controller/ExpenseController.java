package com.ssamba.petsi.expense_service.domain.expense.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssamba.petsi.expense_service.domain.expense.dto.request.*;
import com.ssamba.petsi.expense_service.domain.expense.service.ExpenseService;
import com.ssamba.petsi.expense_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.expense_service.global.exception.ExceptionCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
@Tag(name = "ExpenseController", description = "소비 컨트롤러")
public class ExpenseController {

    private final ExpenseService expenseService;

    // 구매 내역 직접 등록
    @PostMapping("/manual")
    public ResponseEntity<?> expenseManualCreate(@RequestHeader("X-User-Id") Long userId,
                                                 @RequestPart("reqDto") String reqDto,
                                                 @RequestPart(value = "image", required = false) MultipartFile img) {
        // JSON 데이터를 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        PurchaseMenualPostRequestDto purchaseDto;
        try {
            purchaseDto = objectMapper.readValue(reqDto, PurchaseMenualPostRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_DATA_FORMAT);
        }

        expenseService.saveExpenseMenual(userId, purchaseDto, img);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 구매 목록 자동 등록
    @PostMapping("/ai")
    public ResponseEntity<?> expenseAiCreate(@RequestHeader("X-User-Id") Long userId,
                                             @RequestBody List<@Valid PurchaseAiPostRequestDto> purchaseDtos) {
        expenseService.saveExpenseAi(userId, purchaseDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 의료비 등록
    @PostMapping("/medical-expense")
    public ResponseEntity<?> medicalExepnseCreate(@RequestHeader("X-User-Id") Long userId,
                                                  @RequestBody @Valid MedicalExpensePostRequestDto medicalExpenseDto) {
        expenseService.saveExpenseMedicalExpense(userId, medicalExpenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 구매 목록 전체 조회
    @GetMapping
    public ResponseEntity<?> findPurchases(@RequestHeader("X-User-Id") Long userId,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "startDate", required = false) LocalDate startDate,
                                        @RequestParam(name = "endDate", required = false) LocalDate endDate) {
        if(endDate == null) {
            endDate = LocalDate.now();
        }

        if(startDate == null) {
            startDate = LocalDate.of(2000, 1, 1);
        }

        return ResponseEntity.ok(expenseService.getExpenses(userId, page, startDate, endDate));
    }

    // 구매 내역 상세 조회
    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<?> findPurchase(@RequestHeader("X-User-Id") Long userId,
                                          @PathVariable("purchaseId") Long purchaseId) {
        return ResponseEntity.ok(expenseService.getPurchase(userId, purchaseId));
    }

    // 의료비 내역 상세 조회
    @GetMapping("/medical-expense/{medicalExpenseId}")
    public ResponseEntity<?> findMedicalExpense(@RequestHeader("X-User-Id") Long userId,
                                                @PathVariable("medicalExpenseId") Long medicalExpenseId) {
        return ResponseEntity.ok(expenseService.getMedicalExpense(userId, medicalExpenseId));
    }

    // 구매 내역 수정
    @PutMapping("/purchase")
    public ResponseEntity<?> updatePurchase(@RequestHeader("X-User-Id") Long userId,
                                            @RequestBody @Valid PurchaseUpdateDto purchaseUpdateDto) {
        expenseService.updatePurchase(userId, purchaseUpdateDto);
        return ResponseEntity.ok().build();
    }

    // 의료비 수정
    @PutMapping("/medical-expense")
    public ResponseEntity<?> updateMedicalExpense(@RequestHeader("X-User-Id") Long userId,
                                                  @RequestBody @Valid MedicalExpenseUpdateDto purchaseUpdateDto) {
        expenseService.updateMedicalExpense(userId, purchaseUpdateDto);
        return ResponseEntity.ok().build();
    }

    // 구매 내역 삭제
    @DeleteMapping("/purchase")
    public ResponseEntity<?> deletePurchase(@RequestHeader("X-User-Id") Long userId,
                                            @RequestBody @Valid Map<String, Long> reqDto) {
        long purchaseId = reqDto.get("purchaseId");

        expenseService.deletePurchase(userId, purchaseId);
        return ResponseEntity.noContent().build();
    }

    // 의료비 삭제
    @DeleteMapping("/medical-expense")
    public ResponseEntity<?> deleteMedicalExpense(@RequestHeader("X-User-Id") Long userId,
                                                  @RequestBody @Valid Map<String, Long> reqDto) {
        long medicalExpenseId = reqDto.get("medicalExpenseId");

        expenseService.deleteMedicalExpense(userId, medicalExpenseId);
        return ResponseEntity.noContent().build();
    }

}
