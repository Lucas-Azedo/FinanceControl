package com.example.FinanceControl.dto.request.userUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateNameRequestDTO {
    @NotBlank(message = "Novo nome é obrigatório")
    private String name;

    @NotBlank(message = "Senha é obrigatória")
    private String password;
}
