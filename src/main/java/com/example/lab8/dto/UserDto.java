package com.example.lab8.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    /** Имя при регистрации. */
    @NotBlank(message = "Введите имя")
    @Size(max = 50, message = "Имя не длиннее 50 символов")
    private String firstName;

    /** Фамилия при регистрации. */
    @NotBlank(message = "Введите фамилию")
    @Size(max = 50, message = "Фамилия не длиннее 50 символов")
    private String lastName;

    /** Электронная почта для входа. */
    @NotBlank(message = "Введите электронную почту")
    @Email(message = "Введите корректную электронную почту")
    @Size(max = 254, message = "Почта не длиннее 254 символов")
    private String email;

    /** Пароль из формы регистрации. */
    @NotBlank(message = "Введите пароль")
    @Size(
            min = 8,
            max = 64,
            message = "Пароль должен содержать от 8 до 64 символов"
    )
    private String password;
}