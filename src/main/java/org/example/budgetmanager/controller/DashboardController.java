package org.example.budgetmanager.controller;


import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.dto.DashboardResponseDto;
import org.example.budgetmanager.models.dto.ExpenseDto;
import org.example.budgetmanager.service.ExpenseService;
import org.example.budgetmanager.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> addExpense(@RequestBody Expense expenseReq) {
        Expense savedExpense = expenseService.save(expenseReq);
        return ResponseEntity.status(HttpStatus.OK)
                .body(savedExpense);
    }


    @GetMapping("/dashboards")
    public ResponseEntity<?> showDashBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year
    ) {
        int resolvedMonth = month == null ? Utils.CURRENT_MONTH : month;
        int resolvedYear = year == null ? Utils.CURRENT_YEAR : year;
        DashboardResponseDto response = expenseService.getReports(page, size, resolvedMonth, resolvedYear);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> showAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<ExpenseDto> transactions = expenseService.getAllTransactions(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactions);
    }
}
