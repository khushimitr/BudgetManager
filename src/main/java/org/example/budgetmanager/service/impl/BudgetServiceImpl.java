package org.example.budgetmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.exception.general.ResourceNotFoundException;
import org.example.budgetmanager.mappers.BudgetMapper;
import org.example.budgetmanager.models.domain.Budget;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.models.dto.RequestDTOs.BudgetRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.BudgetResponseDto;
import org.example.budgetmanager.repository.BudgetRepository;
import org.example.budgetmanager.service.BudgetService;
import org.example.budgetmanager.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    private final BudgetMapper budgetMapper;

    public BudgetServiceImpl(BudgetRepository budgetRepository, BudgetMapper budgetMapper) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
    }

    @Override
    public BudgetResponseDto updateBudget(BudgetRequestDto budgetRequestDto, int month, int year) {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Budget existingBudget = budgetRepository.findCurrBudgetByUserId(userId, month, year).orElseThrow(
                () -> new ResourceNotFoundException("Budget doesn't exist for user with id : " + userId)
        );
        existingBudget.setBudget(budgetRequestDto.getBudget());

        Budget savedBudget = budgetRepository.save(existingBudget);
        BudgetResponseDto responseDto = budgetMapper.toResponseDTO(savedBudget);
        log.debug("Updated the budget entry : {}", savedBudget);
        return responseDto;
    }

    @Override
    public Page<BudgetResponseDto> getAllBudgets(int page, int size) {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<BudgetResponseDto> responseDtoPage = budgetRepository.getAllByUser_Id(userId, pageable).map(b -> budgetMapper.toResponseDTO(b));
        return responseDtoPage;
    }

    @Override
    public BudgetResponseDto getCurrentBudget(int month, int year) {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Budget currBudget = budgetRepository.findCurrBudgetByUserId(userId, month, year).orElseThrow(
                () -> new ResourceNotFoundException("Budget doesn't exist for user with id : " + userId)
        );
        BudgetResponseDto responseDto = budgetMapper.toResponseDTO(currBudget);
        log.debug("Current Budget is : {}", responseDto);
        return responseDto;
    }


    @Override
    public void initialSaveForUser(User user) {
        Budget budget = Budget.builder()
                .budget(BigDecimal.ZERO)
                .user(user)
                .build();

        budgetRepository.save(budget);
    }

}
