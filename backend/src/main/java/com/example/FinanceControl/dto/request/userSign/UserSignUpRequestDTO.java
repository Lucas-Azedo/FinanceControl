package com.example.FinanceControl.dto.request.userSign;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @NotNull(message = "Senha não pode ser nula")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;
}
