package org.example.budgetmanager.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.models.dto.RequestDTOs.LoginReqDto;
import org.example.budgetmanager.models.dto.RequestDTOs.RegisterReqDto;
import org.example.budgetmanager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterReqDto registerReqDto) {
        return authService.register(registerReqDto);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginReqDto loginReqDto) {
        return authService.login(loginReqDto);
    }
}

