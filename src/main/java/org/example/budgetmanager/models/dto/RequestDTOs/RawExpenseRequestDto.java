package org.example.budgetmanager.models.dto.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RawExpenseRequestDto {
    private String description;
}