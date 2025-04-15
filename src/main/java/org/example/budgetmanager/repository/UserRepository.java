package org.example.budgetmanager.repository;

import org.example.budgetmanager.models.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserById(Integer id);

    @Override
    void deleteById(Integer integer);
}
