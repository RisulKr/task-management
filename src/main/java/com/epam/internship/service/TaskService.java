package com.epam.internship.service;

import com.epam.internship.dto.TaskDTO;


import java.util.List;

public interface TaskService {

    void createTask(String username,TaskDTO taskDto);

    void updateTask(Long id, String username, TaskDTO taskDto);

    void deleteTask(Long id, String username);

    TaskDTO getTask(Long id, String username);

    List<TaskDTO> getAllTask(String username);
}
