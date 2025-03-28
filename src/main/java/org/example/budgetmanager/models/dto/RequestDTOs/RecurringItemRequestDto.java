package org.example.budgetmanager.models.dto.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.enums.Category;
import org.example.budgetmanager.models.enums.ExpenseType;
import org.example.budgetmanager.models.enums.Status;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecurringItemRequestDto {

    private ExpenseType expenseType;
    private BigDecimal amount;
    private String serviceName;
    private String cronExpression;
    private Status status;
    private Category category;


}