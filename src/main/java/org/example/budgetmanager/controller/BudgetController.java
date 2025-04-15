package org.example.budgetmanager.controller;

import org.example.budgetmanager.models.dto.RequestDTOs.BudgetRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.BudgetResponseDto;
import org.example.budgetmanager.service.BudgetService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/budget")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PutMapping
    ResponseEntity<?> updateBudget(
            @RequestBody BudgetRequestDto requestDto,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year
    ) {
        BudgetResponseDto responseDto = budgetService.updateBudget(requestDto, month, year);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping
    ResponseEntity<?> getCurrentBudget(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year
    ) {
        BudgetResponseDto responseDto = budgetService.getCurrentBudget(month, year);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping("/history")
    ResponseEntity<?> getAllBudgets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<BudgetResponseDto> responseDto = budgetService.getAllBudgets(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }
}
