package org.example.budgetmanager.models.dto;

import lombok.*;
import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ExpenseResponseDto {

    private String description;
    private String category;
    private ExpenseType expenseType;
    private BigDecimal amount;
    private LocalDate date;

    public static ExpenseResponseDto toDto(Expense expense) {
        return ExpenseResponseDto.builder()
                .category(expense.getCategory().getName())
                .description(expense.getDescription())
                .expenseType(expense.getExpenseType())
                .date(expense.getDate())
                .amount(expense.getAmount())
                .build();
    }

    public static List<ExpenseResponseDto> expenseListToExpenseDtoList(List<Expense> expenses) {
        return expenses.stream().map(ExpenseResponseDto::toDto).toList();
    }

}