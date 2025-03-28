package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.models.dto.RequestDTOs.BudgetRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.BudgetResponseDto;
import org.springframework.data.domain.Page;

public interface BudgetService {
    BudgetResponseDto updateBudget(BudgetRequestDto budgetRequestDto);

    Page<BudgetResponseDto> getAllBudgets(int page, int size);
}
