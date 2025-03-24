package org.example.budgetmanager.utils;

import org.example.budgetmanager.models.domain.Category;
import org.example.budgetmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    CategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> categories = List.of(
                "Rent",
                "Bills & Utilities",
                "Groceries",
                "Ordering In",
                "Dining out",
                "Entertainment",
                "Transportation",
                "Car",
                "Shopping",
                "Travel",
                "Investment",
                "Cosmetic",
                "Pets",
                "Subscriptions",
                "Healthcare",
                "Gym",
                "Income",
                "Saving",
                "General",
                "Booze",
                "SplittedEntry"
        );

        categories.forEach(category -> {
            if (!categoryService.existByName(category))
                categoryService.save(Category.builder().name(category).build());
        });
    }
}
