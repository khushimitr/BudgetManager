package org.example.budgetmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.domain.Category;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogHistoryDto {

    private String description;
    private Category category;
    private BigDecimal amount;
    private LocalDateTime date;
    private ExpenseType expenseType;
}