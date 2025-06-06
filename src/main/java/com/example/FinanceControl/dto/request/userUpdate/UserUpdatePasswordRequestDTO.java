package com.example.FinanceControl.dto.request.userUpdate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordRequestDTO {
    private String newPassword;
    private String password;
}
