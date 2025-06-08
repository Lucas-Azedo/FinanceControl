package com.example.FinanceControl.controller;

import com.example.FinanceControl.dto.request.userSign.UserSignInRequestDTO;
import com.example.FinanceControl.dto.request.userSign.UserSignUpRequestDTO;
import com.example.FinanceControl.dto.response.UserSignInResponseDTO;
import com.example.FinanceControl.dto.response.UserSignUpResponseDTO;
import com.example.FinanceControl.service.user.UserSignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints para cadastro e login de usuários")
public class AuthController {

    private final UserSignService userSignService;

    @Operation(summary = "Cadastrar novo usuário")
    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDTO> signUp(@RequestBody @Valid UserSignUpRequestDTO dto) {
        return ResponseEntity.ok(userSignService.signUp(dto));
    }

    @Operation(summary = "Login de usuário")
    @PostMapping("/signin")
    public ResponseEntity<UserSignInResponseDTO> signIn(@RequestBody @Valid UserSignInRequestDTO dto) {
        return ResponseEntity.ok(userSignService.signIn(dto));
    }
}
