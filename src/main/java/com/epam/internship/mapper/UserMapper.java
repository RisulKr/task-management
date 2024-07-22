package com.epam.internship.mapper;

import com.epam.internship.dto.RoleDto;
import com.epam.internship.dto.UserDto;
import com.epam.internship.entity.User;
import com.epam.internship.exception.RoleNotFoundException;
import com.epam.internship.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleRepository roleRepository;

    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .roles(user.getRoles().stream()
                        .map(role -> new RoleDto(role.getId(), role.getRoleName()))
                        .collect(Collectors.toSet()))
                .build();
    }

    public User userDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .enabled(userDto.isEnabled())
                .roles(userDto.getRoles().stream()
                        .map(roleDto -> {
                            return roleRepository.findById(roleDto.getId())
                                    .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + roleDto.getId()));
                        })
                        .collect(Collectors.toSet()))
                .build();
    }
}
