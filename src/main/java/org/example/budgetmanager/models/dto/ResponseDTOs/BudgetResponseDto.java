package org.example.budgetmanager.models.dto.ResponseDTOs;

import lombok.*;
import org.example.budgetmanager.models.domain.User;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetResponseDto {

    private Long id;
    private User user;
    private String month;
    private String year;
    private BigDecimal budget;
}