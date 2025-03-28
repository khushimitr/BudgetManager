package org.example.budgetmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.budgetmanager.models.domain.Category;
import org.example.budgetmanager.models.domain.Recurrings;
import org.example.budgetmanager.models.enums.ExpenseType;
import org.example.budgetmanager.models.enums.Status;
import org.springframework.scheduling.support.CronExpression;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecurringsDto {

    private ExpenseType expenseType;
    private BigDecimal amount;
    private String serviceName;
    private LocalDateTime date;
    private Status status;
    private Category category;


    public static RecurringsDto toDto(Recurrings recurringItem) {
        return RecurringsDto.builder()
                .expenseType(recurringItem.getExpenseType())
                .amount(recurringItem.getAmount())
                .serviceName(recurringItem.getServiceName())
                .status(recurringItem.getStatus())
                .category(recurringItem.getCategory())
                .date(getNextExecutionDate(recurringItem.getCronExpression()))
                .build();
    }

    public static LocalDateTime getNextExecutionDate(String cronExpression) {
        try {
            CronExpression cron = CronExpression.parse(cronExpression);
            ZonedDateTime now = ZonedDateTime.now(); // Uses the system's default timezone
            ZonedDateTime nextExecution = cron.next(now);
            return (nextExecution != null) ? nextExecution.toLocalDateTime() : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}