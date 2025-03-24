package org.example.budgetmanager.service;

import jakarta.transaction.Transactional;
import org.example.budgetmanager.models.dto.LoanDto;

public interface LoanService {
    @Transactional
    void save(LoanDto loanDto);
}
