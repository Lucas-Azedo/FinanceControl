package com.example.FinanceControl.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInResponseDTO {
    UUID id;
    String token;
}
