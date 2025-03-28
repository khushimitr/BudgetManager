package org.example.budgetmanager.models.dto.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpenseSummaryDto {
    ExpenseType expenseType; // x-axis
    BigDecimal amount; // y-axis
}
