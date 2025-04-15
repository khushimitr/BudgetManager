package org.example.budgetmanager.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.models.dto.RequestDTOs.LoginReqDto;
import org.example.budgetmanager.models.dto.RequestDTOs.RegisterReqDto;
import org.example.budgetmanager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterReqDto registerReqDto) {
        return authService.register(registerReqDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginReqDto loginReqDto) {
        return authService.login(loginReqDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return authService.logout();
    }

    @GetMapping("/getme")
    public ResponseEntity<?> getMe() {
        return authService.getMe();
    }
}

