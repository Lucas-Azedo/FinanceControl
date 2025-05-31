package com.example.FinanceControl.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateResponseDTO {
        UUID id;
        String name;
        String email;
}
