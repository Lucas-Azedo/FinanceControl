package com.example.FinanceControl.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    String password;
    String name;
    String email;
}
