package org.example.budgetmanager.models.dto.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashboardResponseDto {
    List<ExpenseSummaryDto> expenseSummary;
    List<CategoryExpendSummaryDto> categoryExpendSummary;
    List<MonthlyExpendSummaryDto> monthlyExpendSummary;
    BudgetExpenseSummaryDto budgetExpenseSummary;
}
