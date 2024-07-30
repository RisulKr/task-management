package com.epam.internship.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterUserDTO {
    @NotEmpty(message = "Provide the number")
    @Pattern(regexp = "^\\+998\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}$",message = "Invalid number")
    private String number;

    @NotEmpty(message = "Username cannot be empty")
    private String userName;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
