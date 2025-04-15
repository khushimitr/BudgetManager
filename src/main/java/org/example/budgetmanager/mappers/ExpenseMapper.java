package org.example.budgetmanager.mappers;

import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.dto.ExpenseClassifierResponse;
import org.example.budgetmanager.models.dto.RequestDTOs.ExpenseRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseResponseDto;
import org.example.budgetmanager.utils.Utils;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Component
public class ExpenseMapper implements DTOMapper<Expense, ExpenseRequestDto, ExpenseResponseDto> {
    @Override
    public Expense toEntity(ExpenseRequestDto requestDto) {
        // user will be filled at service layer
        return Expense.builder()
                .description(requestDto.getDescription())
                .expenseType(requestDto.getExpenseType())
                .amount(requestDto.getAmount())
                .date(requestDto.getDate())
                .category(requestDto.getCategory())
                .build();
    }

    @Override
    public ExpenseResponseDto toResponseDTO(Expense entity) {
        return ExpenseResponseDto.builder()
                .id(entity.getId())
                .category(entity.getCategory())
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

    public ExpenseRequestDto toRequestDto(ExpenseClassifierResponse result, String description) {
        return ExpenseRequestDto.builder()
                .description(description)
                .category(result.getCategory())
                .amount(result.getAmount())
                .expenseType(Utils.getExpenseTypeFromCategory(result.getCategory()))
                .date(LocalDate.now())
                .build();
    }
}