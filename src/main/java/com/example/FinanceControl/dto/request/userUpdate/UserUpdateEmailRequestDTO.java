package com.example.FinanceControl.dto.request.userUpdate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateEmailRequestDTO {
    private String email;
    private String password;
}
