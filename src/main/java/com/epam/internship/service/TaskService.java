package com.epam.internship.service;

import com.epam.internship.dto.TaskDTO;
import com.epam.internship.dto.TaskSelectDTO;
import com.epam.internship.enums.Status;
import org.springframework.data.domain.Page;

public interface TaskService {

    void createTask(String username,TaskDTO taskDto);

    void updateTask(Long id, String username, TaskDTO taskDto);

    void deleteTask(Long id, String username);

    TaskSelectDTO getTask(Long id, String username);

    Page<TaskSelectDTO> getAllTask(String username, int pageNo, int pageSize, String sortBy, String direction);
    Page<TaskSelectDTO> getAllFavouriteTask(String username, int pageNo, int pageSize, String sortBy, String direction);

    Page<TaskSelectDTO> getAllTaskByStatus(String username, Status status, int pageNo, int pageSize, String sortBy, String direction);
}
