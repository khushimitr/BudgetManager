package org.example.budgetmanager.repository;

import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.dto.CategoryExpendSummaryDto;
import org.example.budgetmanager.models.dto.ExpenseDto;
import org.example.budgetmanager.models.dto.ExpenseSummaryDto;
import org.example.budgetmanager.models.dto.MonthlyExpendSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query("""
            SELECT NEW org.example.budgetmanager.models.dto.ExpenseDto(
                e.description,
                c.name,
                e.expenseType,
                e.amount,
                e.date
            )
            FROM Expense e
            JOIN e.category c
            WHERE e.user.id = :userId
            """)
    Page<ExpenseDto> findByUserIdOrderByDateDesc(
            @Param("userId") Integer userId,
            Pageable pageable
    );

    @Query("""
            SELECT NEW org.example.budgetmanager.models.dto.ExpenseSummaryDto(e.expenseType, SUM(e.amount))
            FROM Expense e
            WHERE e.user.id = :userId
              AND EXTRACT(YEAR FROM e.date) = :year
              AND EXTRACT(MONTH FROM e.date) = :month
            GROUP BY e.expenseType
            """)
    List<ExpenseSummaryDto> getExpenseSummariesByUserId(@Param("userId") Integer userId, @Param("month") Integer month, @Param("year") Integer year);

    @Query("""
            SELECT NEW org.example.budgetmanager.models.dto.CategoryExpendSummaryDto(c.name,SUM(e.amount))
            FROM Expense e JOIN e.category c
            WHERE e.expenseType = 'EXPEND' AND e.user.id = :userId
              AND EXTRACT(YEAR FROM e.date) = :year
              AND EXTRACT(MONTH FROM e.date) = :month
            GROUP BY c.name
            """)
    List<CategoryExpendSummaryDto> getCategoryExpenditureSummary(@Param("userId") Integer userId, @Param("month") Integer month, @Param("year") Integer year);


    @Query("""
            SELECT NEW org.example.budgetmanager.models.dto.MonthlyExpendSummaryDto(TO_CHAR(e.date, 'Month') AS month,
                SUM(e.amount) AS totalAmount)
            FROM Expense e
            WHERE e.expenseType = 'EXPEND'
              AND e.user.id = :userId
              AND EXTRACT(YEAR FROM e.date) = :year
            GROUP BY TO_CHAR(e.date, 'Month'), EXTRACT(MONTH FROM e.date)
            ORDER BY EXTRACT(MONTH FROM e.date)
            """)
    List<MonthlyExpendSummaryDto> getMonthlyExpenditureSummary(@Param("userId") Integer userId, @Param("year") Integer year);
}
