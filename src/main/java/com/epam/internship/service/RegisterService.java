package com.epam.internship.service;

import com.epam.internship.dto.RegisterUserDTO;

public interface RegisterService {
    String registerUser(RegisterUserDTO userDto);
}
