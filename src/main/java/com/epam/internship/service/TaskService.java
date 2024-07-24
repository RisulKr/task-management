package com.epam.internship.service;

import com.epam.internship.dto.TaskDTO;


import java.util.List;

public interface TaskService {

    void createTask(TaskDTO taskDto);

    TaskDTO updateTask(Long id, TaskDTO taskDto);

    void deleteTask(Long id);

    TaskDTO getTask(Long id);

    List<TaskDTO> getAllTask();
}
