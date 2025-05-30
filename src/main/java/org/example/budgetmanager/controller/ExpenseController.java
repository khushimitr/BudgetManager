package org.example.budgetmanager.controller;


import org.example.budgetmanager.mappers.ExpenseMapper;
import org.example.budgetmanager.models.dto.ExpenseClassifierResponse;
import org.example.budgetmanager.models.dto.RequestDTOs.ExpenseRequestDto;
import org.example.budgetmanager.models.dto.RequestDTOs.RawExpenseRequestDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseResponseDto;
import org.example.budgetmanager.service.ExpenseClassifierService;
import org.example.budgetmanager.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    private final ExpenseClassifierService classifierService;

    private final ExpenseMapper expenseMapper;

    public ExpenseController(ExpenseService expenseService, ExpenseClassifierService classifierService, ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.classifierService = classifierService;
        this.expenseMapper = expenseMapper;
    }

    @PostMapping
    public ResponseEntity<?> addExpense(@RequestBody RawExpenseRequestDto expenseReq) {
        ExpenseClassifierResponse response = classifierService.categorize(expenseReq.getDescription());
        ExpenseRequestDto requestDto = expenseMapper.toRequestDto(response, expenseReq.getDescription());
        ExpenseResponseDto responseDto = expenseService.createExpense(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<?> updateExpense(
            @RequestBody ExpenseRequestDto expenseReq,
            @PathVariable Integer expenseId
    ) throws AccessDeniedException {
        ExpenseResponseDto responseDto = expenseService.updateExpense(expenseId, expenseReq);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable Integer expenseId
    ) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/history")
    public ResponseEntity<?> showAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<ExpenseResponseDto> expenseHistory = expenseService.getExpenseLogHistory(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(expenseHistory);
    }
}
