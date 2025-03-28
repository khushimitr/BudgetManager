package org.example.budgetmanager.models.dto.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.enums.Category;
import org.example.budgetmanager.models.enums.ExpenseType;
import org.example.budgetmanager.models.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecurringItemResponseDto {

    private Integer id;
    private ExpenseType expenseType;
    private BigDecimal amount;
    private String serviceName;
    private LocalDateTime nextDueDate;
    private String cronExpression;
    private Status status;
    private Category category;

}