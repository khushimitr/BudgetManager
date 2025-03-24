package org.example.budgetmanager.service;

import org.example.budgetmanager.models.domain.Expense;

public interface ExpenseService {

    Expense save(Expense expenseReq);
}
