package com.ssamba.petsi.expense_service.domain.expense.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssamba.petsi.expense_service.domain.expense.dto.request.*;
import com.ssamba.petsi.expense_service.domain.expense.service.ExpenseService;
import com.ssamba.petsi.expense_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.expense_service.global.exception.ExceptionCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
@Tag(name = "ExpenseController", description = "소비 컨트롤러")
public class ExpenseController {

    private final ExpenseService expenseService;

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

    @PostMapping("/ai")
    public ResponseEntity<?> expenseAiCreate(@RequestHeader("X-User-Id") Long userId,
                                             @RequestBody PurchaseAiPostRequestDto purchaseDto) {
        expenseService.saveExpenseAi(userId, purchaseDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/medical-expense")
    public ResponseEntity<?> medicalExepnseCreate(@RequestHeader("X-User-Id") Long userId,
                                                  @RequestBody MedicalExpensePostRequestDto medicalExpenseDto) {
        expenseService.saveExpenseMedicalExpense(userId, medicalExpenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> findPurchases(@RequestHeader("X-User-Id") Long userId,
                                        @RequestParam(name = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(expenseService.findAll(userId, page));
    }

    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<?> findPurchase(@RequestHeader("X-User-Id") Long userId,
                                          @PathVariable("purchaseId") Long purchaseId) {
        return ResponseEntity.ok(expenseService.findPurchase(userId, purchaseId));
    }

    @GetMapping("/expense/medical-expense/{medicalExpenseId}")
    public ResponseEntity<?> findMedicalExpense(@RequestHeader("X-User-Id") Long userId,
                                                @PathVariable("medicalExpenseId") Long medicalExpenseId) {
        return ResponseEntity.ok(expenseService.findMedicalExpense(userId, medicalExpenseId));
    }

    @PutMapping("/expense/purchase")
    public ResponseEntity<?> updatePurchase(@RequestHeader("X-User-Id") Long userId,
                                            @RequestBody PurchaseUpdateDto purchaseUpdateDto) {
        expenseService.updatePurchase(userId, purchaseUpdateDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/expense/medical-expense")
    public ResponseEntity<?> updateMedicalExpense(@RequestHeader("X-User-Id") Long userId,
                                                  @RequestBody MedicalExpenseUpdateDto purchaseUpdateDto) {
        expenseService.updateMedicalExpense(userId, purchaseUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deletePurchase(@RequestHeader("X-User-Id") Long userId, @RequestBody Map<String, Long> reqDto) {
        long purchaseId = reqDto.get("purchaseId");

        expenseService.deletePurchase(userId, purchaseId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMedicalExpense(@RequestHeader("X-User-Id") Long userId, @RequestBody Map<String, Long> reqDto) {
        long medicalExpenseId = reqDto.get("medicalExpenseId");

        expenseService.deleteMedicalExpense(userId, medicalExpenseId);
        return ResponseEntity.noContent().build();
    }

}
