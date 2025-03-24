package org.example.budgetmanager.service;

import org.example.budgetmanager.models.dto.LoginReqDto;
import org.example.budgetmanager.models.dto.RegisterReqDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> login(LoginReqDto loginReqDto);

    ResponseEntity<?> register(RegisterReqDto registerReqDto);

}
