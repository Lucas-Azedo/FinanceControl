package com.example.FinanceControl.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInRequestDTO {
    private String email;
    private String password;
}
