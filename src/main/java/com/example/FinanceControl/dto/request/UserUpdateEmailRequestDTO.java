package com.example.FinanceControl.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateEmailRequestDTO {
    private String email;
    private String password;
}
