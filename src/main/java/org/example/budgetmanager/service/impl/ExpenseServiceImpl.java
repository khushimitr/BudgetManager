package org.example.budgetmanager.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.mappers.ExpenseMapper;
import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.models.dto.RequestDTOs.ExpenseRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.*;
import org.example.budgetmanager.models.enums.ExpenseType;
import org.example.budgetmanager.repository.ExpenseRepository;
import org.example.budgetmanager.service.ExpenseService;
import org.example.budgetmanager.service.UserService;
import org.example.budgetmanager.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserService userService;

    @Autowired
    ExpenseMapper expenseMapper;

    @Transactional
    @Override
    public ExpenseResponseDto createExpense(ExpenseRequestDto expenseReq) {
        Expense expense = expenseMapper.toEntity(expenseReq);
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        User proxyUser = userService.getReferenceById(userId);

        expense.setUser(proxyUser);
        Expense savedExpense = expenseRepository.save(expense);
        ExpenseResponseDto responseDTO = expenseMapper.toResponseDTO(savedExpense);
        log.debug("Created a new expense entry : {}", responseDTO);
        return responseDTO;
    }


    @Override
    public void deleteExpense(Integer expenseId) {
        log.debug("Deleted expense entry with id : {}", expenseId);
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public ExpenseResponseDto updateExpense(Integer expenseId, ExpenseRequestDto expenseRequest) throws AccessDeniedException {
        Expense existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (!existingExpense.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You cannot update this expense");
        }

        // Update only allowed fields
        if (expenseRequest.getAmount() != null) {
            existingExpense.setAmount(Utils.processAmount(expenseRequest.getAmount()));
        }
        if (expenseRequest.getDescription() != null) {
            existingExpense.setDescription(expenseRequest.getDescription());
        }
        if (expenseRequest.getCategory() != null) {
            existingExpense.setCategory(expenseRequest.getCategory());
        }
        if (expenseRequest.getDate() != null) {
            existingExpense.setDate(expenseRequest.getDate());
        }
        if (expenseRequest.getExpenseType() != null) {
            existingExpense.setExpenseType(expenseRequest.getExpenseType());
        }

        Expense savedExpense = expenseRepository.save(existingExpense);
        ExpenseResponseDto responseDTO = expenseMapper.toResponseDTO(savedExpense);
        log.debug("Modified expense entry : {}", responseDTO);
        return responseDTO;
    }

    @Override
    public ExpenseResponseDto saveRecurringItems(Expense expenseReq) {
        return expenseMapper.toResponseDTO(expenseRepository.save(expenseReq));
    }


    @Override
    public DashboardResponseDto getReports(int page, int size, int month, int year) {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        List<ExpenseSummaryDto> expenseSummaryDtoList = expenseRepository.getExpenseSummariesByUserId(userId, month, year);
        for (ExpenseType expenseType : ExpenseType.values()) {
            if (expenseSummaryDtoList.stream().noneMatch(x -> x.getExpenseType() == expenseType)) {
                expenseSummaryDtoList.add(ExpenseSummaryDto.builder()
                        .expenseType(expenseType)
                        .amount(BigDecimal.ZERO)
                        .build());
            }
        }

        List<CategoryExpendSummaryDto> categoryExpendSummaryDtoList = expenseRepository.getCategoryExpenditureSummary(userId, month, year);


        List<MonthlyExpendSummaryDto> monthlyExpendSummaryDtoList = expenseRepository.getMonthlyExpenditureSummary(userId, year);
        for (Month m : Month.values()) {
            if (monthlyExpendSummaryDtoList.stream().noneMatch(x -> Objects.equals(x.getMonth(), m.getDisplayName(TextStyle.FULL, Locale.ENGLISH)))) {
                monthlyExpendSummaryDtoList.add(MonthlyExpendSummaryDto.builder()
                        .month(m.getDisplayName(TextStyle.FULL, Locale.ENGLISH))
                        .totalExpendAmount(BigDecimal.ZERO)
                        .totalIncomeAmount(BigDecimal.ZERO)
                        .build());
            }
        }
        monthlyExpendSummaryDtoList.sort(Comparator.comparing(
                obj -> Month.valueOf(obj.getMonth().toUpperCase())
        ));

        return DashboardResponseDto.builder()
                .expenseSummary(expenseSummaryDtoList)
                .categoryExpendSummary(categoryExpendSummaryDtoList)
                .monthlyExpendSummary(monthlyExpendSummaryDtoList)
                .logHistory(getExpenseLogHistory(page, size))
                .build();
    }

    @Override
    public Page<ExpenseResponseDto> getExpenseLogHistory(int page, int size) {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        return expenseRepository.findByUserIdOrderByDateDesc(userId, pageable);
    }
}
