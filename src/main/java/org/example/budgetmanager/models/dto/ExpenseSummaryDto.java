package org.example.budgetmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSummaryDto {
    ExpenseType expenseType; // x-axis
    BigDecimal amount; // y-axis
}
