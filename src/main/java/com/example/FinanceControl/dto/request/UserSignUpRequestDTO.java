package com.example.FinanceControl.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequestDTO {
    private String name;
    private String email;
    private String password;
}
