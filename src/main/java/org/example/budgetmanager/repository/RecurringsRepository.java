package org.example.budgetmanager.repository;

import org.example.budgetmanager.models.domain.RecurringItem;
import org.example.budgetmanager.models.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecurringsRepository extends JpaRepository<RecurringItem, Integer> {

    List<RecurringItem> findByStatus(Status status);

    @Override
    Optional<RecurringItem> findById(Integer recurringItemId);

    @Override
    void deleteById(Integer recurringItemId);

    
    Page<RecurringItem> findAllByUser_Id(Integer userId, Pageable pageable);
}
