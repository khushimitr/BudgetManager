package org.example.budgetmanager.service;

import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.dto.RequestDTOs.ExpenseRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.DashboardResponseDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseResponseDto;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;

public interface ExpenseService {

    ExpenseResponseDto createExpense(ExpenseRequestDto expenseReq);

    void deleteExpense(Integer expenseId);

    ExpenseResponseDto updateExpense(Integer expenseId, ExpenseRequestDto expenseRequest) throws AccessDeniedException;

    ExpenseResponseDto saveRecurringItems(Expense expenseReq);

    DashboardResponseDto getReports(int page, int size, int month, int year);

    Page<ExpenseResponseDto> getExpenseLogHistory(int page, int size);
}
