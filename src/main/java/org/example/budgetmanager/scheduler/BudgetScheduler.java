package org.example.budgetmanager.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseResponseDto;
import org.example.budgetmanager.models.enums.Status;
import org.example.budgetmanager.repository.RecurringsRepository;
import org.example.budgetmanager.service.ExpenseService;
import org.example.budgetmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class RecurringItemScheduler {

    @Autowired
    RecurringsRepository recurringsRepository;

    @Autowired
    ExpenseService expenseService;

    @Autowired
    UserService userService;


    @Scheduled(cron = "${cron.jobs.recurringitem}")
    public void processDueRecurringItems() {
        recurringsRepository.findByStatus(Status.ACTIVE)
                .stream()
                .filter(item -> isDueToday(item.getCronExpression()))
                .forEach(item -> {
                    User user = userService.getUserById(item.getUser().getId());
                    if (null == user) {
                        log.error("ERROR : User no longer exists...");
                    } else {
                        Expense toSaveExpense = Expense.builder()
                                .description(item.getServiceName())
                                .category(item.getCategory())
                                .expenseType(item.getExpenseType())
                                .date(LocalDate.now())
                                .amount(item.getAmount())
                                .user(user)
                                .build();
                        ExpenseResponseDto savedExpenseRespDto = expenseService.saveRecurringItems(toSaveExpense);
                        log.info("Recurring Item saved : {}", savedExpenseRespDto);
                    }
                });
    }


    private boolean isDueToday(String cronExpression) {
        try {
            CronExpression cron = CronExpression.parse(cronExpression);
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime nextRun = cron.next(now.minusSeconds(1)); // Check from just before now

            return nextRun != null &&
                    nextRun.toLocalDate().isEqual(now.toLocalDate());
        } catch (IllegalArgumentException e) {
            log.error("Invalid cron expression: {}", cronExpression);
            return false;
        }
    }

}
