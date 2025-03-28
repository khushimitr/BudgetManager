package org.example.budgetmanager.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.mappers.BudgetMapper;
import org.example.budgetmanager.models.domain.Budget;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.repository.BudgetRepository;
import org.example.budgetmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BudgetScheduler {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    UserService userService;

    @Autowired
    BudgetMapper budgetMapper;

    @Scheduled(cron = "${cron.jobs.budget}")
    public void processBudgetForNextMonth() {
        budgetRepository.findLatestBudgetForEachUser().forEach(
                prevBudget -> {
                    User proxyUser = userService.getReferenceById(prevBudget.getUser().getId());
                    Budget newBudget = Budget.builder()
                            .budget(prevBudget.getBudget())
                            .user(proxyUser)
                            .build();

                    Budget savedBudget = budgetRepository.save(newBudget);
                    log.debug("Scheduler :: savedBudget : {}", budgetMapper.toResponseDTO(savedBudget));
                }
        );
    }
}
