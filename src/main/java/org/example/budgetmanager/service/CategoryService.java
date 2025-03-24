package org.example.budgetmanager.service;

import org.example.budgetmanager.models.domain.Category;

import java.util.Optional;

public interface CategoryService {
    Category save(Category category);

    Optional<Category> findById(Integer id);

    Boolean existByName(String name);
}
