package org.example.budgetmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.enums.Category;
import org.example.budgetmanager.models.enums.ExpenseType;
import org.example.budgetmanager.models.enums.SplitType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {

    private Integer id;
    private String description;
    private ExpenseType expenseType;
    private BigDecimal amount;
    private Integer fromUserId;
    private List<Integer> toUserIds;
    private List<String> usernames;
    private Category category;
    private SplitType splitType;
    private List<BigDecimal> shares;
}