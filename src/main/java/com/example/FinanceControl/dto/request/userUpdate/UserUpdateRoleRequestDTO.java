package com.example.FinanceControl.dto.request.userUpdate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRoleRequestDTO {

    @NotBlank(message = "Role é obrigatório")
    private String roleName;
}
