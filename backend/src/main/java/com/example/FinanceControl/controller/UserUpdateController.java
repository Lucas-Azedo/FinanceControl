package com.example.FinanceControl.controller;

import com.example.FinanceControl.dto.request.userUpdate.UserUpdateEmailRequestDTO;
import com.example.FinanceControl.dto.request.userUpdate.UserUpdateNameRequestDTO;
import com.example.FinanceControl.dto.request.userUpdate.UserUpdatePasswordRequestDTO;
import com.example.FinanceControl.service.user.UserUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@Tag(name = "User Self Update", description = "Endpoints para atualização dos dados do próprio usuário")
@RequiredArgsConstructor
public class UserUpdateController {

    private final UserUpdateService userUpdateService;

    @Operation(summary = "Atualiza o e-mail do usuário")
    @PutMapping("/email")
    public ResponseEntity<Void> updateEmail(@RequestBody @Valid UserUpdateEmailRequestDTO dto) {
        userUpdateService.updateEmail(dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza o nome do usuário")
    @PutMapping("/name")
    public ResponseEntity<Void> updateName(@RequestBody @Valid UserUpdateNameRequestDTO dto) {
        userUpdateService.updateName(dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza a senha do usuário")
    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserUpdatePasswordRequestDTO dto) {
        userUpdateService.updatePassword(dto);
        return ResponseEntity.noContent().build();
    }
}
