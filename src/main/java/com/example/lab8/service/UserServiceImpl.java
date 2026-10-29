package com.example.lab8.service;

import com.example.lab8.dto.UserDto;
import com.example.lab8.entity.Role;
import com.example.lab8.entity.User;
import com.example.lab8.repository.RoleRepository;
import com.example.lab8.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Регистрация нового пользователя. */
    @Override
    @Transactional
    public void saveUser(UserDto userDto) {
        String email = normalizeEmail(userDto.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Пользователь с такой почтой уже зарегистрирован"
            );
        }

        if (userDto.getPassword()
                .getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new IllegalArgumentException(
                    "Пароль слишком длинный для BCrypt. Используйте более короткий пароль"
            );
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_USER");
                    return roleRepository.save(newRole);
                });

        User user = new User();
        user.setFirstName(userDto.getFirstName().trim());
        user.setLastName(userDto.getLastName().trim());
        user.setEmail(email);
        user.setPassword(
                passwordEncoder.encode(userDto.getPassword())
        );
        user.getRoles().add(role);

        userRepository.save(user);
    }

    /** Проверка занятости электронной почты. */
    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(normalizeEmail(email));
    }

    /** Список пользователей без паролей и их хешей. */
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        return userRepository.findAll(Sort.by("id"))
                .stream()
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /** Приведение почты к единому виду. */
    private String normalizeEmail(String email) {
        return email == null
                ? ""
                : email.trim().toLowerCase(Locale.ROOT);
    }
}