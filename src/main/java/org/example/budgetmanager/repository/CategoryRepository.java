package org.example.budgetmanager.repository;

import org.example.budgetmanager.models.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findById(Integer id);

    Boolean existsByName(String name);
}
