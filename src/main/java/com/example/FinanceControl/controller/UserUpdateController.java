package com.example.FinanceControl.controller;

import com.example.FinanceControl.service.user.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserUpdateController {

    private final UserUpdateService userUpdateService;
}
