package com.ssamba.petsi.expense_service.domain.expense.service;

import com.ssamba.petsi.expense_service.domain.expense.dto.request.*;
import com.ssamba.petsi.expense_service.domain.expense.dto.response.ExpenseResponseDto;
import com.ssamba.petsi.expense_service.domain.expense.repository.MedicalExpenseRepository;
import com.ssamba.petsi.expense_service.domain.expense.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final PurchaseRepository purchaseRepository;
    private final MedicalExpenseRepository medicalExpenseRepository;

    public void saveExpenseMenual(Long userId, PurchaseMenualPostRequestDto purchaseDto, MultipartFile img) {
    }

    public void saveExpenseAi(Long userId, PurchaseAiPostRequestDto purchaseDto) {
    }

    public void saveExpenseMedicalExpense(Long userId, MedicalExpensePostRequestDto medicalExpenseDto) {
    }

    public List<ExpenseResponseDto> findAll(Long userId, int page) {
        return null;
    }

    public ExpenseResponseDto findPurchase(Long userId, Long purchaseId) {
        return null;
    }

    public ExpenseResponseDto findMedicalExpense(Long userId, Long medicalExpenseId) {
        return null;
    }

    public void updatePurchase(Long userId, PurchaseUpdateDto purchaseUpdateDto) {

    }

    public void updateMedicalExpense(Long userId, MedicalExpenseUpdateDto purchaseUpdateDto) {
    }

    public void deletePurchase(Long userId, long purchaseId) {
    }

    public void deleteMedicalExpense(Long userId, long medicalExpenseId) {
    }
}
