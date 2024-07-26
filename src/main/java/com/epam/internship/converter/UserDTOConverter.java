package com.epam.internship.converter;

import com.epam.internship.dto.UserDTO;
import com.epam.internship.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDTOConverter extends DTOConverter<User, UserDTO> {

    public UserDTOConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<User> getTypeEntity() {
        return User.class;
    }

    @Override
    protected Class<UserDTO> getTypeDTO() {
        return UserDTO.class;
    }
}
