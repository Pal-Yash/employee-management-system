package com.example.employee_management_system.controller;

import com.example.employee_management_system.dto.LoginRequestDTO;
import com.example.employee_management_system.dto.LoginResponseDTO;
import com.example.employee_management_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(userService.loginUser(dto));
    }
}