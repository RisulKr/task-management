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
        String role = user.getRoles().stream()
                .findFirst()
                .map(Role::getRoleName)
                .orElse(null);
        userDTO.setRole(role);
        return userDTO;
    }
}
