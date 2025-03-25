package org.example.budgetmanager.service;

import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.dto.DashboardResponseDto;
import org.example.budgetmanager.models.dto.ExpenseDto;
import org.springframework.data.domain.Page;

public interface ExpenseService {

    Expense save(Expense expenseReq);


    DashboardResponseDto getReports(int page, int size, int month, int year);

    Page<ExpenseDto> getAllTransactions(int page, int size);
}
