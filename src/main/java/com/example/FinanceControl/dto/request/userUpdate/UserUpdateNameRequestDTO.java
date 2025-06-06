package com.example.FinanceControl.dto.request.userUpdate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateNameRequestDTO {
    private String name;
    private String password;
}
