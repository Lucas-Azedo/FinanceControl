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
    public ResponseEntity<UserUpdateEmailRequestDTO> updateEmail(@RequestBody @Valid UserUpdateEmailRequestDTO dto) {
        return ResponseEntity.ok(userUpdateService.updateEmail(dto));
    }

    @Operation(summary = "Atualiza o nome do usuário")
    @PutMapping("/name")
    public ResponseEntity<UserUpdateNameRequestDTO> updateName(@RequestBody @Valid UserUpdateNameRequestDTO dto) {
        return ResponseEntity.ok(userUpdateService.updateName(dto));
    }

    @Operation(summary = "Atualiza a senha do usuário")
    @PutMapping("/password")
    public ResponseEntity<UserUpdatePasswordRequestDTO> updatePassword(@RequestBody @Valid UserUpdatePasswordRequestDTO dto) {
        return ResponseEntity.ok(userUpdateService.updatePassword(dto));
    }
}
