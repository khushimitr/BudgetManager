package org.example.budgetmanager.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterReqDto {
    private String username;
    private String password;
    private String email;
}
