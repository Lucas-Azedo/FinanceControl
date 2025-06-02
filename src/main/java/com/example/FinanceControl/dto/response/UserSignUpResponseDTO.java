package com.example.FinanceControl.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpResponseDTO {
    UUID id;
    String token;
    String name;
    String email;
}
