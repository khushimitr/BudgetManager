package org.example.budgetmanager.models.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRespDto {
    private Integer id;
    private String username;
    private String email;
}
