package org.example.budgetmanager.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.config.security.jwt.JwtUtils;
import org.example.budgetmanager.config.security.service.UserDetailsImpl;
import org.example.budgetmanager.exception.general.BadRequestException;
import org.example.budgetmanager.exception.general.ResourceNotFoundException;
import org.example.budgetmanager.models.domain.User;
import org.example.budgetmanager.models.dto.RequestDTOs.LoginReqDto;
import org.example.budgetmanager.models.dto.RequestDTOs.RegisterReqDto;
import org.example.budgetmanager.models.dto.ResponseDTOs.AuthRespDto;
import org.example.budgetmanager.service.AuthService;
import org.example.budgetmanager.service.BudgetService;
import org.example.budgetmanager.service.UserService;
import org.example.budgetmanager.utils.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final BudgetService budgetService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService, PasswordEncoder passwordEncoder, BudgetService budgetService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.budgetService = budgetService;
    }

    @Override
    public ResponseEntity<?> login(LoginReqDto loginReqDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(), loginReqDto.getPassword()
            ));
        } catch (AuthenticationException e) {
            throw new ResourceNotFoundException("Bad Credentials");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUserDetails(userDetails);

        User user = userService.getUserById(userDetails.getId());

        ResponseCookie cookie = ResponseCookie.from("access_token", jwtToken)
                .httpOnly(true)
                .secure(true) // Enable in production (HTTPS)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Lax") // or "Strict"
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AuthRespDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .avatarUrl(user.getAvatarUrl())
                        .build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> register(RegisterReqDto registerReqDto) {
        if (userService.existsByUsername(registerReqDto.getUsername())) {
            throw new BadRequestException("Error: Username is already taken!");
        }

        if (userService.existsByEmail(registerReqDto.getEmail())) {
            throw new BadRequestException("Error: Email is already in use!");
        }
        User user = User.builder()
                .username(registerReqDto.getUsername())
                .password(passwordEncoder.encode(registerReqDto.getPassword()))
                .email(registerReqDto.getEmail())
                .avatarUrl(Utils.AVATAR_URL + registerReqDto.getUsername())
                .build();

        User savedUser = userService.save(user);

        String jwtToken = jwtUtils.generateTokenFromUserDetails(UserDetailsImpl.builder()
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .id(savedUser.getId())
                .authorities(null)
                .password(savedUser.getPassword())
                .build());

        // initial call to save budget as 0
        budgetService.initialSaveForUser(savedUser);

        ResponseCookie cookie = ResponseCookie.from("access_token", jwtToken)
                .httpOnly(true)
                .secure(true) // Enable in production (HTTPS)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Lax") // or "Strict"
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AuthRespDto.builder()
                        .id(savedUser.getId())
                        .username(savedUser.getUsername())
                        .email(savedUser.getEmail())
                        .avatarUrl(savedUser.getAvatarUrl())
                        .build());
    }

    @Override
    public ResponseEntity<?> getMe() {
        Integer userId = Utils.getCurrentUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(AuthRespDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .avatarUrl(user.getAvatarUrl())
                        .build());
    }

    @Override
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true) // Enable in production (HTTPS)
                .path("/")
                .maxAge(0)
                .sameSite("Lax") // or "Strict"
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.LOCATION, "/")
                .build();
    }
}
