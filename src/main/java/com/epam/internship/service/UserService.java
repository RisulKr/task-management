package com.epam.internship.service;

import com.epam.internship.dto.UserDTO;

import java.util.List;

public interface UserService {
    String removeUser(Integer id);
    List<UserDTO> findAllUsers();
    UserDTO findUser(Integer id);
    String  assignAdmin(String username,Integer id);
}
