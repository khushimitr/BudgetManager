package org.example.budgetmanager.controller;


import org.example.budgetmanager.models.dto.RequestDTOs.RecurringItemRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.RecurringItemResponseDto;
import org.example.budgetmanager.service.RecurringItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/subscription")
public class RecurringItemController {

    private final RecurringItemService recurringItemService;

    public RecurringItemController(RecurringItemService recurringItemService) {
        this.recurringItemService = recurringItemService;
    }

    @PostMapping
    public ResponseEntity<?> addRecurringItem(@RequestBody RecurringItemRequestDto recurringItemReq) {
        RecurringItemResponseDto responseDto = recurringItemService.createRecurringItem(recurringItemReq);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @PutMapping("/{recurringItemId}")
    public ResponseEntity<?> updateRecurringItem(
            @RequestBody RecurringItemRequestDto recurringItemReq,
            @PathVariable Integer recurringItemId
    ) throws AccessDeniedException {
        RecurringItemResponseDto responseDto = recurringItemService.updateRecurringItem(recurringItemId, recurringItemReq);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @DeleteMapping("/{recurringItemId}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable Integer recurringItemId
    ) {
        recurringItemService.deleteRecurringItem(recurringItemId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<?> showAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<RecurringItemResponseDto> responseDtos = recurringItemService.getAllRecurringItems(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDtos);
    }
}
