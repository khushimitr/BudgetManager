package org.example.budgetmanager.models.dto.RequestDTOs;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetRequestDto {
    private BigDecimal budget;
}