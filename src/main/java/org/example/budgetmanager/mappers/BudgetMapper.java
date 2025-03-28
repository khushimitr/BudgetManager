package org.example.budgetmanager.mappers;

import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.dto.RequestDTOs.ExpenseRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseResponseDto;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.List;

@Component
public class ExpenseMapper implements DTOMapper<Expense, ExpenseRequestDto, ExpenseResponseDto> {
    @Override
    public Expense toEntity(ExpenseRequestDto requestDto) {
        // category and user will be filled at service layer
        return Expense.builder()
                .description(requestDto.getDescription())
                .expenseType(requestDto.getExpenseType())
                .amount(requestDto.getAmount())
                .date(requestDto.getDate())
                .build();
    }

    @Override
    public ExpenseResponseDto toResponseDTO(Expense entity) {
        return ExpenseResponseDto.builder()
                .id(entity.getId())
                .category(entity.getCategory().getName())
                .description(entity.getDescription())
                .expenseType(entity.getExpenseType())
                .date(entity.getDate())
                .amount(entity.getAmount().setScale(2, RoundingMode.HALF_UP))
                .build();
    }

    @Override
    public List<Expense> toEntityList(List<ExpenseRequestDto> requestDtoList) {
        return requestDtoList.stream().map(this::toEntity).toList();
    }

    @Override
    public List<ExpenseResponseDto> toResponseDTOList(List<Expense> entityList) {
        return entityList.stream().map(this::toResponseDTO).toList();
    }
}