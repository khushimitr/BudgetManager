package org.example.budgetmanager.models.dto.ResponseDTOs;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@Data
public class MonthlyExpendSummaryDto {
    String month;
    BigDecimal totalExpendAmount;
    BigDecimal totalIncomeAmount;


    public MonthlyExpendSummaryDto(String month, BigDecimal totalExpendAmount, BigDecimal totalIncomeAmount) {
        this.month = month.trim();
        this.totalExpendAmount = totalExpendAmount;
        this.totalIncomeAmount = totalIncomeAmount;
    }
}
