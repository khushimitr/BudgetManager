package org.example.budgetmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.mappers.RecurringItemMapper;
import org.example.budgetmanager.models.domain.Category;
import org.example.budgetmanager.models.domain.RecurringItem;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.models.dto.RequestDTOs.RecurringItemRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.RecurringItemResponseDto;
import org.example.budgetmanager.repository.RecurringsRepository;
import org.example.budgetmanager.service.CategoryService;
import org.example.budgetmanager.service.RecurringService;
import org.example.budgetmanager.service.UserService;
import org.example.budgetmanager.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Slf4j
@Service
public class RecurringServiceImpl implements RecurringService {

    @Autowired
    RecurringsRepository recurringsRepository;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RecurringItemMapper recurringItemMapper;


    @Override
    public RecurringItemResponseDto createRecurringItem(RecurringItemRequestDto recurringItemRequest) {
        RecurringItem recurringItem = recurringItemMapper.toEntity(recurringItemRequest);

        String username = Utils.getCurrentUsernameFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        User user = userService.getUserByUsername(username);
        Category category = categoryService.findById(recurringItem.getCategory().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found")
        );

        recurringItem.setUser(user);
        recurringItem.setCategory(category);
        RecurringItem savedRecurringItem = recurringsRepository.save(recurringItem);
        log.debug("Created a new recurring Item : {}", savedRecurringItem);
        return recurringItemMapper.toResponseDTO(savedRecurringItem);
    }

    @Override
    public void deleteRecurringItem(Integer recurringItemId) {
        log.debug("Deleted recurring Item entry with id : {}", recurringItemId);
        recurringsRepository.deleteById(recurringItemId);
    }

    @Override
    public RecurringItemResponseDto updateExpense(Integer recurringItemId, RecurringItemRequestDto recurringItemRequest) throws AccessDeniedException {
        RecurringItem existingRecurringItem = recurringsRepository.findById(recurringItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Recurring Item not found"));

        String username = Utils.getCurrentUsernameFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (!existingRecurringItem.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You cannot update this Recurring Item");
        }

        // Update only allowed fields
        if (recurringItemRequest.getAmount() != null) {
            existingRecurringItem.setAmount(Utils.processAmount(recurringItemRequest.getAmount()));
        }
        if (recurringItemRequest.getServiceName() != null) {
            existingRecurringItem.setServiceName(recurringItemRequest.getServiceName());
        }
        if (recurringItemRequest.getCategoryId() != null) {
            Category category = categoryService.findById(recurringItemRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingRecurringItem.setCategory(category);
        }
        if (recurringItemRequest.getCronExpression() != null) {
            existingRecurringItem.setCronExpression(recurringItemRequest.getCronExpression());
        }
        if (recurringItemRequest.getExpenseType() != null) {
            existingRecurringItem.setExpenseType(recurringItemRequest.getExpenseType());
        }
        if (recurringItemRequest.getStatus() != null) {
            existingRecurringItem.setStatus(recurringItemRequest.getStatus());
        }

        RecurringItem savedRecurringItem = recurringsRepository.save(existingRecurringItem);
        log.debug("Modified recurringItem entry : {}", savedRecurringItem);
        return recurringItemMapper.toResponseDTO(savedRecurringItem);
    }


    @Override
    public Page<RecurringItemResponseDto> getAllRecurringItems(int page, int size) {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Pageable pageable = PageRequest.of(page, size, Sort.by("created_at").descending());

        return recurringsRepository.findAllByUser_Id(userId, pageable).map(item -> recurringItemMapper.toResponseDTO(item));
    }

}
