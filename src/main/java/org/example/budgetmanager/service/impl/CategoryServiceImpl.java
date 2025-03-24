package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.models.domain.Category;
import org.example.budgetmanager.repository.CategoryRepository;
import org.example.budgetmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Boolean existByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
