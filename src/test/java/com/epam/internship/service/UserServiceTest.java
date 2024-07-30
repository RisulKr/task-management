package com.epam.internship.service;

import com.epam.internship.converter.UserDTOConverter;
import com.epam.internship.dto.UserDTO;
import com.epam.internship.entity.Role;
import com.epam.internship.entity.User;
import com.epam.internship.exception.UserNotFoundException;
import com.epam.internship.repository.RoleRepository;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.impl.UserServiceImpl;
import com.epam.internship.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.security.InvalidParameterException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserDTOConverter converter;

    @InjectMocks
    private UserServiceImpl userService;



    @Test
    void deleteUser_throwsUserNotException_WhenUserIsNotPresent() {
        Integer id = 11;
        when(userRepository.findByIdAndEnabledIsTrue(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.removeUser(id));
    }

    @Test
    void deletesUser_WhenUserIsPresent() {
        Integer id = 1;
        User userToSearch = User.builder()
                .id(1)
                .enabled(true)
                .password("123")
                .username("USERNAME")
                .build();

        User expected = User.builder()
                .id(1)
                .enabled(false)
                .password("123")
                .username("USERNAME")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(userToSearch));

        Optional<User> userToRemove = userRepository.findById(id);
        User result = userToRemove.get();
        result.setEnabled(false);
        assertEquals(expected, result);
    }

    @Test
    void findsUser() {
        Integer id = 1;
        User user = User.builder()
                .id(1)
                .enabled(true)
                .password("123")
                .username("USERNAME")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .enabled(true)
                .password("123")
                .username("USERNAME")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(converter.toDto(user)).thenReturn(userDTO);

        Optional<User> result = userRepository.findById(id);
        assertTrue(result.isPresent());
        assertEquals(userDTO, converter.toDto(user));
    }

    @Test
    void throwsUserNotFoundException_WhenUserNotPresent() {
        Integer id = 100;
        when(userRepository.findByIdAndEnabledIsTrue(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUser(id));
    }

    @Test
    void findAllUsers_returnsUserDTOList_whenUsersFound() {
        User user1 = User.builder().id(1).enabled(true).username("user1").build();
        User user2 = User.builder().id(2).enabled(true).username("user2").build();
        List<User> users = List.of(user1, user2);

        UserDTO userDTO1 = UserDTO.builder().id(1).username("user1").build();
        UserDTO userDTO2 = UserDTO.builder().id(2).username("user2").build();
        List<UserDTO> userDTOs = List.of(userDTO1, userDTO2);

        when(userRepository.findAllByEnabledIsTrue()).thenReturn(Optional.of(users));
        when(converter.toDtoList(users)).thenReturn(userDTOs);

        List<UserDTO> result = userService.findAllUsers();

        assertEquals(userDTOs, result);
    }

    @Test
    void findAllUsers_throwsUserNotFoundException_whenNoUsersFound() {
        when(userRepository.findAllByEnabledIsTrue()).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findAllUsers());
    }

    @Test
    void assignsAdminSuccessfully_WhenValidParameters() {
        String username = "User";
        Integer id = 1;
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        user.setRoles(new ArrayList<>());

        Role adminRole = Role.builder().roleName("ADMIN").build();

        when(userRepository.findByUsernameAndId(username, id)).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(Optional.of(adminRole));


        String result = userService.assignAdmin(username, id);

        Optional<User> userFound = userRepository.findByUsernameAndId(username, id);
        assertTrue(userFound.isPresent());
        assertEquals(username, userFound.get().getUsername());
        assertEquals(id, userFound.get().getId());


        assertEquals(MessageUtils.role_assignment_message, result);
        assertTrue(user.getRoles().contains(adminRole));
        verify(userRepository).save(user);
    }

    @Test
    void assignAdminInvalidId_WhenInvalidId() {
        String username = "User";
        Integer id = null;
        assertThrows(InvalidParameterException.class, () -> userService.assignAdmin(username, id));
    }

    @Test
    void assignAdminNullUsername_WhenInvalidUsername() {
        String username = null;
        Integer id = 1;
        assertThrows(InvalidParameterException.class, () -> userService.assignAdmin(username, id));
    }

    @Test
    void throwsUserNotFoundException_WhenAssigningAdminRole() {
        String username = "User";
        Integer id = 1;
        assertThrows(UserNotFoundException.class, () -> userService.assignAdmin(username, id));
    }
}
