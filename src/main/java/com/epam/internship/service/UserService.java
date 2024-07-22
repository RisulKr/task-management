package com.epam.internship.service;

import com.epam.internship.dto.UserDto;

import java.util.List;

public interface UserService {
    String removeUser(Integer id);
    List<UserDto> findAllUsers();
    UserDto findUser(Integer id);
    UserDto findUserByUsername(String username);
}
