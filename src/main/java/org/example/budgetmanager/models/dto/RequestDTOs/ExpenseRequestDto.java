package org.example.budgetmanager.models.dto.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.enums.Category;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpenseRequestDto {

    private String description;
    private Category category;
    private ExpenseType expenseType;
    private BigDecimal amount;
    private LocalDate date;
}