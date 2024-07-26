package com.epam.internship.service.impl;

import com.epam.internship.converter.RegisterUserDTOConverter;
import com.epam.internship.converter.UserDTOConverter;
import com.epam.internship.dto.RegisterUserDTO;
import com.epam.internship.entity.Role;
import com.epam.internship.entity.User;
import com.epam.internship.exception.RoleNotFoundException;
import com.epam.internship.exception.UserRegistrationException;
import com.epam.internship.repository.RoleRepository;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.RegisterService;
import com.epam.internship.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
        try {
            userRepository.save(processRegistration(userDto));
            return MessageUtils.register_success;
        } catch (DataIntegrityViolationException | JpaSystemException | RoleNotFoundException exception) {
            log.info(exception.getMessage());
            throw new UserRegistrationException(exception.getMessage(), exception);
        }
    }

    private User processRegistration(RegisterUserDTO registerUserDTO) {
        User userEntity = registerUserDTOConverter.toEntity(registerUserDTO);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        roles.add(userRole);
        userEntity.setRoles(roles);
        userEntity.setEnabled(true);
        userEntity.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        return userEntity;
    }
}