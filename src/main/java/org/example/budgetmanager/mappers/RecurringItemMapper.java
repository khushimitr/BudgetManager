package org.example.budgetmanager.mappers;

import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.models.domain.RecurringItem;
import org.example.budgetmanager.models.dto.RequestDTOs.RecurringItemRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.RecurringItemResponseDto;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;


@Slf4j
@Component
public class RecurringItemMapper implements DTOMapper<RecurringItem, RecurringItemRequestDto, RecurringItemResponseDto> {
    @Override
    public RecurringItem toEntity(RecurringItemRequestDto requestDto) {
        // user will be filled at service layer
        return RecurringItem.builder()
                .expenseType(requestDto.getExpenseType())
                .serviceName(requestDto.getServiceName())
                .amount(requestDto.getAmount())
                .cronExpression(requestDto.getCronExpression())
                .status(requestDto.getStatus())
                .category(requestDto.getCategory())
                .build();
    }

    @Override
    public RecurringItemResponseDto toResponseDTO(RecurringItem entity) {
        return RecurringItemResponseDto.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .serviceName(entity.getServiceName())
                .expenseType(entity.getExpenseType())
                .nextDueDate(getNextExecutionDate(entity.getCronExpression()))
                .cronExpression(entity.getCronExpression())
                .amount(entity.getAmount().setScale(2, RoundingMode.HALF_UP))
                .status(entity.getStatus())
                .build();
    }

    @Override
    public List<RecurringItem> toEntityList(List<RecurringItemRequestDto> requestDtoList) {
        return requestDtoList.stream().map(this::toEntity).toList();
    }

    @Override
    public List<RecurringItemResponseDto> toResponseDTOList(List<RecurringItem> entityList) {
        return entityList.stream().map(this::toResponseDTO).toList();
    }

    private LocalDateTime getNextExecutionDate(String cronExpression) {
        try {
            CronExpression cron = CronExpression.parse(cronExpression);
            ZonedDateTime now = ZonedDateTime.now(); // Uses the system's default timezone
            ZonedDateTime nextExecution = cron.next(now);
            return (nextExecution != null) ? nextExecution.toLocalDateTime() : null;
        } catch (IllegalArgumentException e) {
            log.info("In catch block... {}", e.getMessage(), e);
            return null;
        }
    }
}