package com.example.FinanceControl.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordRequestDTO {
    private String newPassword;
    private String password;
}
