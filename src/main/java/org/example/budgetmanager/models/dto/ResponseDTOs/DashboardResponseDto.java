package org.example.budgetmanager.models.dto.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashboardResponseDto {
    List<ExpenseSummaryDto> expenseSummary;
    List<CategoryExpendSummaryDto> categoryExpendSummary;
    Page<ExpenseResponseDto> logHistory;
    List<MonthlyExpendSummaryDto> monthlyExpendSummary;
}
