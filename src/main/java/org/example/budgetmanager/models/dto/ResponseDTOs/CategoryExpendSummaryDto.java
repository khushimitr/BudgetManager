package org.example.budgetmanager.models.dto.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.enums.Category;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryExpendSummaryDto {
    Category category;
    BigDecimal amount;
}
