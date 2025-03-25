package org.example.budgetmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {
    List<ExpenseSummaryDto> expenseSummary;
    List<CategoryExpendSummaryDto> categoryExpendSummary;
    Page<ExpenseDto> logHistory;
    List<MonthlyExpendSummaryDto> monthlyExpendSummary;
}
