package org.example.budgetmanager.models.dto.ResponseDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthRespDto {
    private Integer id;
    private String username;
    private String email;
}
