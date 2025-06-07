package com.example.FinanceControl.controller;

import com.example.FinanceControl.dto.request.userUpdate.UserUpdateEmailRequestDTO;
import com.example.FinanceControl.dto.request.userUpdate.UserUpdateNameRequestDTO;
import com.example.FinanceControl.dto.request.userUpdate.UserUpdatePasswordRequestDTO;
import com.example.FinanceControl.service.user.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserUpdateController {

    private final UserUpdateService userUpdateService;

    @PutMapping("/email")
    public ResponseEntity<UserUpdateEmailRequestDTO> updateEmail(@RequestBody UserUpdateEmailRequestDTO dto) {
        return ResponseEntity.ok(userUpdateService.updateEmail(dto));
    }

    @PutMapping("/name")
    public ResponseEntity<UserUpdateNameRequestDTO> updateName(@RequestBody UserUpdateNameRequestDTO dto) {
        return ResponseEntity.ok(userUpdateService.updateName(dto));
    }

    @PutMapping("/password")
    public ResponseEntity<UserUpdatePasswordRequestDTO> updatePassword(@RequestBody UserUpdatePasswordRequestDTO dto) {
        return ResponseEntity.ok(userUpdateService.updatePassword(dto));
    }
}
