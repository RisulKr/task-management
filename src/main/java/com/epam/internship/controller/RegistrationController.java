package com.epam.internship.controller;

import com.epam.internship.dto.RegisterUserDTO;
import com.epam.internship.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.epam.internship.utils.ControllerUtils.listErrors;
import static com.epam.internship.utils.MessageUtils.*;

@RestController
@RequestMapping("/register")
@Slf4j
@CrossOrigin
@AllArgsConstructor
public class RegistrationController {

    private final RegisterService registerService;

    @PostMapping("/registerUser")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register User", description = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = register_success),
            @ApiResponse(responseCode = "400", description = error_message)
    })
    public ResponseEntity<String> createUser(@RequestBody @Valid RegisterUserDTO registerUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return listErrors(bindingResult);
        }
        registerService.registerUser(registerUserDTO);
        log.info("User {} " + register_message, registerUserDTO.getUserName());
        return new ResponseEntity<>(register_success, HttpStatus.CREATED);
    }
}
