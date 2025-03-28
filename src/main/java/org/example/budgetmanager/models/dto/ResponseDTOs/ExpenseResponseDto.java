package org.example.budgetmanager.models.dto.ResponseDTOs;

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
public class ExpenseResponseDto {

    private Integer id;
    private String description;
    private Category category;
    private ExpenseType expenseType;
    private BigDecimal amount;
    private LocalDate date;
}