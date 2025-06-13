package com.example.FinanceControl.controller;

import com.example.FinanceControl.dto.request.userUpdate.UserUpdateRoleRequestDTO;
import com.example.FinanceControl.dto.response.UserResponseDTO;
import com.example.FinanceControl.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin User Management", description = "Ações administrativas para gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Listar todos os usuários")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Obter usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Alterar a função (role) de um usuário")
    @PutMapping("/{id}/role")
    public ResponseEntity<Void> changeUserRole(
            @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRoleRequestDTO dto) {
        userService.changeUserRole(id, dto.getRoleName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Excluir usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
