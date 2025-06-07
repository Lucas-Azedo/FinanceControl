package com.example.FinanceControl.dto.request.userUpdate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateEmailRequestDTO {
    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @NotBlank
    @NotNull(message = "Senha não pode ser nula")
    private String password;
}
