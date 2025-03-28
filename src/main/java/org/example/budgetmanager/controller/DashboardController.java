package org.example.budgetmanager.controller;


import org.example.budgetmanager.models.dto.ResponseDTOs.DashboardResponseDto;
import org.example.budgetmanager.service.ExpenseService;
import org.example.budgetmanager.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping
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
}
