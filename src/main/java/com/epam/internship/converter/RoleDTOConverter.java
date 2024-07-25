package com.epam.internship.converter;

import com.epam.internship.dto.RoleDTO;
import com.epam.internship.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class RoleDTOConverter extends DTOConverter<Role, RoleDTO>{
    public RoleDTOConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Role> getTypeEntity() {
        return Role.class;
    }

    @Override
    protected Class<RoleDTO> getTypeDTO() {
        return RoleDTO.class;
    }
}
