package org.example.budgetmanager.service;

import org.example.budgetmanager.models.dto.RequestDTOs.LoginReqDto;
import org.example.budgetmanager.models.dto.RequestDTOs.RegisterReqDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> login(LoginReqDto loginReqDto);

    ResponseEntity<?> register(RegisterReqDto registerReqDto);

}
