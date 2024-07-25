package com.epam.internship.converter;

import com.epam.internship.dto.RegisterUserDTO;
import com.epam.internship.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserDTOConverter extends DTOConverter<User, RegisterUserDTO>{

    public RegisterUserDTOConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<User> getTypeEntity() {
        return User.class;
    }

    @Override
    protected Class<RegisterUserDTO> getTypeDTO() {
        return RegisterUserDTO.class;
    }
}
