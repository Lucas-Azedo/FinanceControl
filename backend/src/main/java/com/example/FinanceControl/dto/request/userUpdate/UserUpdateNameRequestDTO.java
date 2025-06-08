package com.example.FinanceControl.dto.request.userUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateNameRequestDTO {
    @NotBlank(message = "Nome e obrigatório")
    private String name;

    @NotBlank(message = "Senha é obrigatória")
    @NotNull(message = "Senha não pode ser nula")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;
}
