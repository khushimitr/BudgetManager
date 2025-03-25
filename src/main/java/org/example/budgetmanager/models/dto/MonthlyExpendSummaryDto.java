package org.example.budgetmanager.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MonthlyExpendSummaryDto {
    String month;
    BigDecimal amount;

    public MonthlyExpendSummaryDto(String month, BigDecimal amount) {
        this.month = month.trim();
        this.amount = amount;
    }
}
