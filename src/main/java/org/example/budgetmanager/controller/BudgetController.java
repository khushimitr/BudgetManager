package org.example.budgetmanager.controller;

import org.example.budgetmanager.models.dto.RequestDTOs.BudgetRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.BudgetResponseDto;
import org.example.budgetmanager.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/budget")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @PutMapping
    ResponseEntity<?> updateBudget(
            @RequestBody BudgetRequestDto requestDto
    ) {
        BudgetResponseDto responseDto = budgetService.updateBudget(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping
    ResponseEntity<?> getCurrentBudget() {
        BudgetResponseDto responseDto = budgetService.getCurrentBudget();
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
