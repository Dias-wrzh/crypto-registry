package com.example.documentsstoragingsepolia.controller;

import com.example.documentsstoragingsepolia.dto.JwtResponse;
import com.example.documentsstoragingsepolia.dto.LoginRequest;
import com.example.documentsstoragingsepolia.dto.RegisterRequest;
import com.example.documentsstoragingsepolia.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public JwtResponse register(@RequestBody  @Valid RegisterRequest request) {
        return authService.register(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}
