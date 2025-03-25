package org.example.budgetmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {

    private String description;
    private String category;
    private ExpenseType expenseType;
    private BigDecimal amount;
    private LocalDate date;

    public static ExpenseDto toDto(Expense expense) {
        return ExpenseDto.builder()
                .category(expense.getCategory().getName())
                .description(expense.getDescription())
                .expenseType(expense.getExpenseType())
                .date(expense.getDate())
                .amount(expense.getAmount())
                .build();
    }

    public static List<ExpenseDto> expenseListToExpenseDtoList(List<Expense> expenses) {
        return expenses.stream().map(ExpenseDto::toDto).toList();
    }

}