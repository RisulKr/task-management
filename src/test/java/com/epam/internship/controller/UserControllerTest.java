package com.epam.internship.controller;

import com.epam.internship.dto.UserDto;
import com.epam.internship.exception.UserNotFoundException;
import com.epam.internship.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.epam.internship.utils.MessageUtils.delete_success;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUser_ShouldReturnOkStatus_WhenUserIsPresent() throws Exception {
        Integer id = 1;
        UserDto userDto = UserDto.builder()
                .username("username")
                .password("password")
                .id(id)
                .enabled(true)
                .build();

        when(userService.findUser(id)).thenReturn(userDto);

        mockMvc.perform(get("/users/getUser/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.username", is("username")));
    }

    @Test
    void getUser_ShouldReturnNotFoundStatus_WhenUserIsNotPresent() throws Exception {
        Integer id = 1;

        when(userService.findUser(id)).thenThrow(new UserNotFoundException("User with id: " + id + " not found"));

        mockMvc.perform(get("/users/getUser/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsers_ShouldReturnOkStatus_WhenUsersArePresent() throws Exception {
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

        List<UserDto> userDtoList = Arrays.asList(userDto1, userDto2);

        when(userService.findAllUsers()).thenReturn(userDtoList);

        mockMvc.perform(get("/users/getUsers/All")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("username1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].username", is("username2")));
    }

    @Test
    void getUsers_ShouldReturnNotFoundStatus_WhenNoUsersArePresent() throws Exception {
        when(userService.findAllUsers()).thenThrow(new UserNotFoundException("No users found"));

        mockMvc.perform(get("/users/getUsers/All")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_ShouldReturnAcceptedStatus_WhenUserIsDeleted() throws Exception {
        Integer id = 1;

        when(userService.removeUser(id)).thenReturn(delete_success);

        mockMvc.perform(delete("/users/deleteUser/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string( delete_success));
    }

    @Test
    void deleteUser_ShouldReturnNotFoundStatus_WhenUserIsNotFound() throws Exception {
        Integer id = 1;

        Mockito.doThrow(new UserNotFoundException("User with id: " + id + " not found")).when(userService).removeUser(id);

        mockMvc.perform(delete("/users/deleteUser/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with id: " + id + " not found"));
    }
}
