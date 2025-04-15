package org.example.budgetmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.exception.general.ForbiddenException;
import org.example.budgetmanager.exception.general.ResourceNotFoundException;
import org.example.budgetmanager.mappers.RecurringItemMapper;
import org.example.budgetmanager.models.domain.RecurringItem;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.models.dto.RequestDTOs.RecurringItemRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.RecurringItemResponseDto;
import org.example.budgetmanager.repository.RecurringsRepository;
import org.example.budgetmanager.service.RecurringItemService;
import org.example.budgetmanager.service.UserService;
import org.example.budgetmanager.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Slf4j
@Service
public class RecurringItemServiceImpl implements RecurringItemService {

    private final RecurringsRepository recurringsRepository;

    private final UserService userService;

    private final RecurringItemMapper recurringItemMapper;

    public RecurringItemServiceImpl(RecurringsRepository recurringsRepository, UserService userService, RecurringItemMapper recurringItemMapper) {
        this.recurringsRepository = recurringsRepository;
        this.userService = userService;
        this.recurringItemMapper = recurringItemMapper;
    }


    @Override
    public RecurringItemResponseDto createRecurringItem(RecurringItemRequestDto recurringItemRequest) {
        RecurringItem recurringItem = recurringItemMapper.toEntity(recurringItemRequest);

        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        User proxyUser = userService.getReferenceById(userId);

        recurringItem.setUser(proxyUser);
        RecurringItem savedRecurringItem = recurringsRepository.save(recurringItem);
        RecurringItemResponseDto responseDTO = recurringItemMapper.toResponseDTO(savedRecurringItem);
        log.debug("Created a new recurring Item : {}", responseDTO);
        return responseDTO;
    }

    @Override
    public void deleteRecurringItem(Integer recurringItemId) {
        log.debug("Deleted recurring Item entry with id : {}", recurringItemId);
        recurringsRepository.deleteById(recurringItemId);
    }

    @Override
    public RecurringItemResponseDto updateRecurringItem(Integer recurringItemId, RecurringItemRequestDto recurringItemRequest) throws AccessDeniedException {
        RecurringItem existingRecurringItem = recurringsRepository.findById(recurringItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Recurring Item not found with id : " + recurringItemId));

        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (!existingRecurringItem.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You cannot update this Recurring Item");
        }

        // Update only allowed fields
        if (recurringItemRequest.getAmount() != null) {
            existingRecurringItem.setAmount(Utils.processAmount(recurringItemRequest.getAmount()));
        }
        if (recurringItemRequest.getServiceName() != null) {
            existingRecurringItem.setServiceName(recurringItemRequest.getServiceName());
        }
        if (recurringItemRequest.getCategory() != null) {
            existingRecurringItem.setCategory(recurringItemRequest.getCategory());
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
        RecurringItemResponseDto responseDTO = recurringItemMapper.toResponseDTO(savedRecurringItem);
        log.debug("Modified recurringItem entry : {}", responseDTO);
        return responseDTO;
    }


    @Override
    public Page<RecurringItemResponseDto> getAllRecurringItems(int page, int size) {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return recurringsRepository.findAllByUser_Id(userId, pageable).map(item -> recurringItemMapper.toResponseDTO(item));
    }

}
