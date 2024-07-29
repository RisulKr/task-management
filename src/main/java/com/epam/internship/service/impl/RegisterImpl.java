package com.epam.internship.service.impl;

import com.epam.internship.converter.RegisterUserDTOConverter;
import com.epam.internship.dto.RegisterUserDTO;
import com.epam.internship.entity.Role;
import com.epam.internship.entity.User;
import com.epam.internship.exception.RoleNotFoundException;
import com.epam.internship.repository.RoleRepository;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.RegisterService;
import com.epam.internship.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RegisterImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RegisterUserDTOConverter registerUserDTOConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(RegisterUserDTO userDto) {
            userRepository.save(processRegistration(userDto));
            return MessageUtils.register_success;
    }

    private User processRegistration(RegisterUserDTO registerUserDTO) {
        User userEntity = registerUserDTOConverter.toEntity(registerUserDTO);
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        roles.add(userRole);
        userEntity.setRoles(roles);
        userEntity.setEnabled(true);
        userEntity.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        return userEntity;
    }
}