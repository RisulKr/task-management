package com.epam.internship.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    private String password;

    private boolean enabled;

    private Set<RoleDTO> roles;
}
