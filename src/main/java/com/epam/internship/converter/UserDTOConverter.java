package com.epam.internship.converter;

import com.epam.internship.dto.RoleDTO;
import com.epam.internship.dto.UserDTO;
import com.epam.internship.entity.Role;
import com.epam.internship.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserDTOConverter extends DTOConverter<User, UserDTO> {

    private final RoleDTOConverter roleDTOConverter;

    public UserDTOConverter(ModelMapper modelMapper, RoleDTOConverter roleDTOConverter) {
        super(modelMapper);
        this.roleDTOConverter = roleDTOConverter;
    }

    @Override
    protected Class<User> getTypeEntity() {
        return User.class;
    }

    @Override
    protected Class<UserDTO> getTypeDTO() {
        return UserDTO.class;
    }

    @Override
    public UserDTO toDto(User user) {
        UserDTO userDTO = super.toDto(user);
        Set<RoleDTO> roles = user.getRoles().stream()
                .map(roleDTOConverter::toDto)
                .collect(Collectors.toSet());
        userDTO.setRoles(roles);
        return userDTO;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        User user = super.toEntity(userDTO);
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleDTOConverter::toEntity)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }
}
