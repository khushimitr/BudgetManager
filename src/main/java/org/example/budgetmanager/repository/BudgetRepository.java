package org.example.budgetmanager.repository;

import org.example.budgetmanager.models.domain.Budget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    @Query("""
            SELECT b from Budget b where b.user.id = :userId
                        AND EXTRACT(YEAR FROM b.createdAt) = :year
                                    AND EXTRACT(MONTH FROM b.createdAt) = :month
            """)
    Optional<Budget> findCurrBudgetByUserId(@Param("userId") Integer userId, @Param("month") Integer month, @Param("year") Integer year);

    Page<Budget> getAllByUser_Id(Integer userId, Pageable pageable);

    @Query("SELECT b FROM Budget b WHERE b.createdAt = " +
            "(SELECT MAX(b2.createdAt) FROM Budget b2 WHERE b2.user.id = b.user.id)")
    List<Budget> findLatestBudgetForEachUser();

}
