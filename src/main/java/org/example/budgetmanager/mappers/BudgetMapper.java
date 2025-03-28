package org.example.budgetmanager.mappers;

import org.example.budgetmanager.models.domain.Budget;
import org.example.budgetmanager.models.dto.RequestDTOs.BudgetRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.BudgetResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetMapper implements DTOMapper<Budget, BudgetRequestDto, BudgetResponseDto> {
    @Override
    public Budget toEntity(BudgetRequestDto requestDto) {
        // month, year and user will be filled at service layer
        return Budget.builder()
                .budget(requestDto.getBudget())
                .build();
    }

    @Override
    public BudgetResponseDto toResponseDTO(Budget entity) {
        return BudgetResponseDto.builder()
                .id(entity.getId())
                .budget(entity.getBudget())
                .month(entity.getCreatedAt().getMonth().toString())
                .year(String.valueOf(entity.getCreatedAt().getYear()))
                .build();
    }

    @Override
    public List<Budget> toEntityList(List<BudgetRequestDto> requestDtoList) {
        return requestDtoList.stream().map(this::toEntity).toList();
    }

    @Override
    public List<BudgetResponseDto> toResponseDTOList(List<Budget> entityList) {
        return entityList.stream().map(this::toResponseDTO).toList();
    }
}