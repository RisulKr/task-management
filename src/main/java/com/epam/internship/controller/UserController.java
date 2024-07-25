package com.epam.internship.controller;

import com.epam.internship.dto.UserDTO;

import com.epam.internship.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.internship.utils.MessageUtils.delete_success;


@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getUser/{id}")
    @Operation(summary = "Find user", description = "Finds a user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUser(
            @PathVariable @Parameter(description = "ID of the user to be found") Integer id) {

            UserDTO dto = userService.findUser(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getUsers/All")
    @Operation(summary = "Find all users", description = "Retrieves all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found")
    })
    public ResponseEntity<List<UserDTO>> getUsers() {

            List<UserDTO> dto = userService.findAllUsers();
            return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error in user deletion"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> deleteUser(
            @PathVariable @Parameter(description = "ID of the user to be deleted") Integer id) {

            userService.removeUser(id);
            return new ResponseEntity<>(delete_success, HttpStatus.ACCEPTED);
    }
}
