package com.example.lab8.controller;

import com.example.lab8.dto.UserDto;
import com.example.lab8.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /** Открытие формы регистрации. */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    /** Обработка данных регистрации. */
    @PostMapping("/register/save")
    public String registerUser(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult bindingResult) {

        if (!bindingResult.hasFieldErrors("email")
                && userService.emailExists(userDto.getEmail())) {

            bindingResult.rejectValue(
                    "email",
                    "email.exists",
                    "Пользователь с такой почтой уже зарегистрирован"
            );
        }

        if (bindingResult.hasErrors()) {
            userDto.setPassword(null);
            return "register";
        }

        try {
            userService.saveUser(userDto);

        } catch (IllegalArgumentException exception) {
            bindingResult.reject(
                    "registration.error",
                    exception.getMessage()
            );

            userDto.setPassword(null);
            return "register";

        } catch (DataIntegrityViolationException exception) {
            bindingResult.reject(
                    "registration.error",
                    "Не удалось зарегистрироваться. Возможно, эта почта уже используется"
            );

            userDto.setPassword(null);
            return "register";
        }

        return "redirect:/register?success";
    }

    /** Открытие формы входа. */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /** Список зарегистрированных пользователей. */
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }
}