package com.epam.internship.mapper;

import com.epam.internship.dto.TaskDto;
import com.epam.internship.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskEntity dtoToEntity(TaskDto taskDto) {
        TaskEntity taskEntity = new TaskEntity();
        dtoToEntity(taskDto, taskEntity);
        return taskEntity;
    }

    public TaskEntity dtoToEntity(TaskDto taskDto, TaskEntity taskEntity) {
        return null;

    }

    public TaskDto entityToDto(TaskEntity taskEntity) {
        return null;

    }
}
