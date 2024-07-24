package com.epam.internship.converter;

import com.epam.internship.converter.util.LocalDateTimeToStringConverter;
import com.epam.internship.converter.util.StringToLocalDateTimeConverter;
import com.epam.internship.dto.TaskDTO;
import com.epam.internship.entity.Task;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskDTOConverter extends DTOConverter<Task, TaskDTO>{

    public TaskDTOConverter(ModelMapper modelMapper) {
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
    protected Class<TaskDTO> getTypeDTO() {
        return TaskDTO.class;
    }
}
