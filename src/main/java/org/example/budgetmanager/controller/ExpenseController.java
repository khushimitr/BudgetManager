package org.example.budgetmanager.controller;


import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> showDashBoards() {

    }
}
