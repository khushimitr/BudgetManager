package org.example.budgetmanager.models.dto;

import org.example.budgetmanager.models.domain.Category;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;
import java.util.List;

class BudgetGraph {
    ExpenseType type; // x-axis
    BigDecimal amount; // y-axis
}

class SpendGraph {
    BigDecimal amount;
    String category;
}

public class DashboardResponseDto {
    List<BudgetGraph> budgetGraph;
    List<SpendGraph> spendGraph;

    List<LogHistoryDto> logHistory;
    List<Category> topCategories;
}
