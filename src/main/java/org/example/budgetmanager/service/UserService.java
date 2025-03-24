package org.example.budgetmanager.service;

import org.example.budgetmanager.models.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User getUserByUsername(String username);

    User getUserById(Integer id);

    User save(User user);

    void deleteById(Integer id);
}
