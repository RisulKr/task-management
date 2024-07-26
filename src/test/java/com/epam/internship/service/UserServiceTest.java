package com.epam.internship.service;

import com.epam.internship.converter.RegisterUserDTOConverter;
import com.epam.internship.dto.UserDTO;
import com.epam.internship.repository.RoleRepository;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    RegisterUserDTOConverter registerUserDTOConverter;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void findUser_ShouldFindUserByID_WhenIDValid(){
        Integer id = 7;
        when(userService.findUser(id)).thenReturn(new UserDTO());

        UserDTO userDTO = userService.findUser(anyInt());
        assertNotNull(userDTO.getRoles());
    }
}
