package org.example.budgetmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {
    List<ExpenseSummaryDto> expenseSummary;
    List<CategoryExpendSummaryDto> categoryExpendSummary;
    Page<ExpenseResponseDto> logHistory;
    List<MonthlyExpendSummaryDto> monthlyExpendSummary;
}
