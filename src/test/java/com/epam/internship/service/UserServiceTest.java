package com.epam.internship.service;


import com.epam.internship.dto.UserDto;
import com.epam.internship.entity.User;
import com.epam.internship.exception.UserNotFoundException;
import com.epam.internship.mapper.UserMapper;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.epam.internship.utils.MessageUtils.removed_message;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserMapper userMapper;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void findByUsername_ReturnUserByUsername_WhenUserExists() {
        User user = User.builder()
                .username("username")
                .password("password")
                .id(1)
                .enabled(true)
                .build();

        UserDto userDto = UserDto.builder()
                .username("username")
                .password("password")
                .id(1)
                .enabled(true)
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto result = userService.findUserByUsername(user.getUsername());

        assertNotNull(result);
        assertEquals(userDto, result);
    }

    @Test
    void findByUsername_ReturnUserNotFound_WhenUserDoesNotExist() {
        User user = User.builder()
                .username("username")
                .password("password")
                .id(100)
                .enabled(true)
                .build();
        when(userRepository.findByUsername(user.getUsername())).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> userService.findUserByUsername(user.getUsername()));
    }

    @Test
    void removeUser_ShouldRemoveUser_WhenUserExists() {
        Integer removedId = 2;
        doNothing().when(userRepository).deleteById(anyInt());
        String result = userService.removeUser(removedId);
        assertEquals(removed_message, result);
    }
    @Test
    void removeUser_ShouldRemoveUser_WhenUserDoesNotExist() {
        Integer removedId = 10;
        when(userService.removeUser(anyInt())).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class,() -> userService.removeUser(removedId));
    }

    @Test
    void findUser_ShouldReturnUserDto_WhenUserExists() {
        Integer id = 1;
        User user = User.builder()
                .username("username")
                .password("password")
                .id(id)
                .enabled(true)
                .build();

        UserDto userDto = UserDto.builder()
                .username("username")
                .password("password")
                .id(id)
                .enabled(true)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto result = userService.findUser(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(userDto, result);
    }
    @Test
    void findUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        Integer id = 100;

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.findUser(id);
        });
    }
    @Test
    void findAllUsers_ShouldReturnListOfUsers_WhenUsersExist() {
        User user1 = User.builder()
                .username("username1")
                .password("password1")
                .id(1)
                .enabled(true)
                .build();

        User user2 = User.builder()
                .username("username2")
                .password("password2")
                .id(2)
                .enabled(true)
                .build();

        UserDto userDto1 = UserDto.builder()
                .username("username1")
                .password("password1")
                .id(1)
                .enabled(true)
                .build();

        UserDto userDto2 = UserDto.builder()
                .username("username2")
                .password("password2")
                .id(2)
                .enabled(true)
                .build();

        List<User> userList = Arrays.asList(user1, user2);
        List<UserDto> userDtoList = Arrays.asList(userDto1, userDto2);

        when(userRepository.findAll()).thenReturn(userList);

        when(userMapper.userToUserDto(user1)).thenReturn(userDto1);
        when(userMapper.userToUserDto(user2)).thenReturn(userDto2);

        List<UserDto> result = userService.findAllUsers();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(userDtoList, result);
    }
}