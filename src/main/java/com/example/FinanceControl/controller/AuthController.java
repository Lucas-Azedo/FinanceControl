package com.example.FinanceControl.controller;

import com.example.FinanceControl.dto.request.UserSignInRequestDTO;
import com.example.FinanceControl.dto.request.UserSignUpRequestDTO;
import com.example.FinanceControl.dto.response.UserSignInResponseDTO;
import com.example.FinanceControl.dto.response.UserSignUpResponseDTO;
import com.example.FinanceControl.service.user.UserSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserSignService userSignService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDTO> signUp(@RequestBody UserSignUpRequestDTO dto) {
        return ResponseEntity.ok(userSignService.signUp(dto));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserSignInResponseDTO> signIn(@RequestBody UserSignInRequestDTO dto) {
        return ResponseEntity.ok(userSignService.signIn(dto));
    }
}
