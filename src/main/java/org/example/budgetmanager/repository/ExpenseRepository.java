package org.example.budgetmanager.repository;

import org.example.budgetmanager.models.domain.Expense;
import org.example.budgetmanager.models.dto.ResponseDTOs.CategoryExpendSummaryDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseResponseDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseSummaryDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.MonthlyExpendSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query("""
            SELECT NEW org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseResponseDto(
                e.id,
                e.description,
                e.category,
                e.expenseType,
                e.amount,
                e.date
            )
            FROM Expense e
            WHERE e.user.id = :userId
            """)
    Page<ExpenseResponseDto> findByUserIdOrderByDateDesc(
            @Param("userId") Integer userId,
            Pageable pageable
    );

    @Query("""
            SELECT NEW org.example.budgetmanager.models.dto.ResponseDTOs.ExpenseSummaryDto(e.expenseType, SUM(e.amount))
            FROM Expense e
            WHERE e.user.id = :userId
              AND EXTRACT(YEAR FROM e.date) = :year
              AND EXTRACT(MONTH FROM e.date) = :month
            GROUP BY e.expenseType
            """)
    List<ExpenseSummaryDto> getExpenseSummariesByUserId(@Param("userId") Integer userId, @Param("month") Integer month, @Param("year") Integer year);

    @Query("""
            SELECT NEW org.example.budgetmanager.models.dto.ResponseDTOs.CategoryExpendSummaryDto(e.category,SUM(e.amount))
            FROM Expense e
            WHERE e.expenseType = 'EXPEND' AND e.user.id = :userId
              AND EXTRACT(YEAR FROM e.date) = :year
              AND EXTRACT(MONTH FROM e.date) = :month
            GROUP BY e.category
            """)
    List<CategoryExpendSummaryDto> getCategoryExpenditureSummary(@Param("userId") Integer userId, @Param("month") Integer month, @Param("year") Integer year);


    @Query("""
            SELECT NEW org.example.budgetmanager.models.dto.ResponseDTOs.MonthlyExpendSummaryDto(TO_CHAR(e.date, 'Month') AS month,
                SUM(e.amount) AS totalExpendAmount,
                SUM(e.amount) AS totalIncomeAmount)
            FROM Expense e
            WHERE e.user.id = :userId
              AND EXTRACT(YEAR FROM e.date) = :year
            GROUP BY e.expenseType, TO_CHAR(e.date, 'Month'), EXTRACT(MONTH FROM e.date)
            ORDER BY EXTRACT(MONTH FROM e.date)
            """)
    List<MonthlyExpendSummaryDto> getMonthlyExpenditureSummary(@Param("userId") Integer userId, @Param("year") Integer year);


    @Override
    Optional<Expense> findById(Integer expenseId);

    @Override
    void deleteById(Integer expenseId);
}
