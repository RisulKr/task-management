package com.epam.internship.service;

import com.epam.internship.dto.TaskDto;


import java.util.List;

public interface TaskService {

    String createTask(TaskDto taskDto);

    TaskDto updateTask(Integer id, TaskDto taskDto);

    String deleteTask(Integer id);

    TaskDto getTask(Integer id);

    List<TaskDto> getAllTask();
}
