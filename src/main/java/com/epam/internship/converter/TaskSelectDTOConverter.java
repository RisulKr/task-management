package com.epam.internship.converter;

import com.epam.internship.converter.util.LocalDateTimeToStringConverter;
import com.epam.internship.converter.util.StringToLocalDateTimeConverter;
import com.epam.internship.dto.TaskSelectDTO;
import com.epam.internship.entity.Task;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskSelectDTOConverter extends DTOConverter<Task, TaskSelectDTO>{
    public TaskSelectDTOConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @PostConstruct
    public void init(){
        modelMapper.addConverter(new LocalDateTimeToStringConverter());
        modelMapper.addConverter(new StringToLocalDateTimeConverter());
    }

    @Override
    protected Class<Task> getTypeEntity() {
        return Task.class;
    }

    @Override
    protected Class<TaskSelectDTO> getTypeDTO() {
        return TaskSelectDTO.class;
    }
}
