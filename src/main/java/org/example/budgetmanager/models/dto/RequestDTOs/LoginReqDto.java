package org.example.budgetmanager.models.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginReqDto {
    private String username;
    private String password;
}
