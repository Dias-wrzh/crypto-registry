package com.example.documentsstoragingsepolia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class LoginRequest {

    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Schema(description = "Пароль", example = "08D_i_a_s01")
    @Size(min = 8, max = 255, message = "Длина пароля должна содержать от 8 до 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

}