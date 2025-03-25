package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.models.domain.Category;
import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.models.dto.*;
import org.example.budgetmanager.repository.ExpenseRepository;
import org.example.budgetmanager.service.CategoryService;
import org.example.budgetmanager.service.ExpenseService;
import org.example.budgetmanager.service.UserService;
import org.example.budgetmanager.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Override
    public Expense save(Expense expenseReq) {
        String username = Utils.getCurrentUsernameFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        User user = userService.getUserByUsername(username);
        Category category = categoryService.findById(expenseReq.getCategory().getId()).orElseThrow();
        expenseReq.setUser(user);
        expenseReq.setCategory(category);
        expenseReq.setAmount(Utils.processAmount(expenseReq.getAmount()));
        return expenseRepository.save(expenseReq);
    }


    @Override
    public DashboardResponseDto getReports(int page, int size, int month, int year) {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        List<ExpenseSummaryDto> expenseSummaryDtoList = expenseRepository.getExpenseSummariesByUserId(userId, month, year);
        List<CategoryExpendSummaryDto> categoryExpendSummaryDtoList = expenseRepository.getCategoryExpenditureSummary(userId, month, year);
        List<MonthlyExpendSummaryDto> monthlyExpendSummaryDtoList = expenseRepository.getMonthlyExpenditureSummary(userId, year);
        return DashboardResponseDto.builder()
                .expenseSummary(expenseSummaryDtoList)
                .categoryExpendSummary(categoryExpendSummaryDtoList)
                .monthlyExpendSummary(monthlyExpendSummaryDtoList)
                .logHistory(getAllTransactions(page, size))
                .build();
    }

    @Override
    public Page<ExpenseDto> getAllTransactions(int page, int size) {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        return expenseRepository.findByUserIdOrderByDateDesc(userId, pageable);
    }
}
