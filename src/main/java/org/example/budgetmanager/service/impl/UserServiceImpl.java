package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.exception.general.ResourceNotFoundException;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User doesn't exist with username : " + username)
        );
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.getUserById(id).orElseThrow(
                () -> new ResourceNotFoundException("User doesn't exist with userId : " + id)
        );
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getReferenceById(Integer userId) {
        return userRepository.getReferenceById(userId);
    }
}
