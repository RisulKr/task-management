package com.epam.internship.service;

import com.epam.internship.converter.RegisterUserDTOConverter;
import com.epam.internship.dto.RegisterUserDTO;
import com.epam.internship.entity.Role;
import com.epam.internship.entity.User;
import com.epam.internship.repository.RoleRepository;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.impl.RegisterImpl;
import com.epam.internship.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RegisterTest {

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    RegisterUserDTOConverter converter;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    RegisterImpl register;

    @Test
    void registersUser() {
        RegisterUserDTO registerUserDTO = RegisterUserDTO.builder()
                .userName("username")
                .password("password123")
                .build();

        Role role = Role.builder().roleName("USER").build();
        User user = User.builder()
                .username("username")
                .password(passwordEncoder.encode("password123"))
                .enabled(true)
                .roles(List.of(role))
                .build();

        when(converter.toEntity(registerUserDTO)).thenReturn(user);
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);

        String result = register.registerUser(registerUserDTO);

        assertEquals(MessageUtils.register_success, result);
        verify(userRepository).save(user);
    }
}
