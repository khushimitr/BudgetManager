package org.example.budgetmanager.service;

import org.example.budgetmanager.models.dto.RequestDTOs.RecurringItemRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.RecurringItemResponseDto;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;

public interface RecurringItemService {
    RecurringItemResponseDto createRecurringItem(RecurringItemRequestDto recurringItemRequest);

    void deleteRecurringItem(Integer recurringItemId);

    RecurringItemResponseDto updateRecurringItem(Integer recurringItemId, RecurringItemRequestDto recurringItemRequest) throws AccessDeniedException;

    Page<RecurringItemResponseDto> getAllRecurringItems(int page, int size);

}
