package com.example.lab8.service;

import com.example.lab8.dto.UserDto;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    boolean emailExists(String email);

    List<UserDto> findAllUsers();
}