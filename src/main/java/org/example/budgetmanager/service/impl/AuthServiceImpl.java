package org.example.budgetmanager.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.config.security.jwt.JwtUtils;
import org.example.budgetmanager.config.security.service.UserDetailsImpl;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.models.dto.AuthRespDto;
import org.example.budgetmanager.models.dto.LoginReqDto;
import org.example.budgetmanager.models.dto.RegisterReqDto;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> login(LoginReqDto loginReqDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(), loginReqDto.getPassword()
            ));
        } catch (AuthenticationException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", "Bad Credentials");
            errorMap.put("status", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(errorMap);
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUserDetails(userDetails);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .body(AuthRespDto.builder()
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> register(RegisterReqDto registerReqDto) {
        if (userRepository.existsByEmail(registerReqDto.getEmail()) || userRepository.existsByUsername(registerReqDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ERROR: email or username already exists");
        }
        User user = User.builder()
                .username(registerReqDto.getUsername())
                .password(passwordEncoder.encode(registerReqDto.getPassword()))
                .email(registerReqDto.getEmail())
                .build();

        User savedUser = userRepository.save(user);

        String jwtToken = jwtUtils.generateTokenFromUserDetails(UserDetailsImpl.builder()
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .id(savedUser.getId())
                .authorities(null)
                .password(savedUser.getPassword())
                .build());
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .body(AuthRespDto.builder()
                        .id(savedUser.getId())
                        .username(savedUser.getUsername())
                        .email(savedUser.getEmail())
                        .build());
    }
}
