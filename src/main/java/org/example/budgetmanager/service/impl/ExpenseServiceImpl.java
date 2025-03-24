package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.models.domain.Category;
import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.models.enums.ExpenseType;
import org.example.budgetmanager.repository.ExpenseRepository;
import org.example.budgetmanager.service.CategoryService;
import org.example.budgetmanager.service.ExpenseService;
import org.example.budgetmanager.service.UserService;
import org.example.budgetmanager.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

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


    public Map<ExpenseType, BigDecimal> getReports() {

    }
}
