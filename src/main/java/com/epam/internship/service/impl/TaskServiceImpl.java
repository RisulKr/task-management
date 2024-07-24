package com.epam.internship.service.impl;

import com.epam.internship.converter.TaskDTOConverter;
import com.epam.internship.dto.TaskDTO;
import com.epam.internship.entity.Task;
import com.epam.internship.exception.TaskNotFoundException;
import com.epam.internship.repository.TaskRepository;
import com.epam.internship.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskDTOConverter taskDTOConverter;
    private final TaskRepository taskRepository;

    @Override
    public void createTask(TaskDTO taskDto) {
        Task task = taskDTOConverter.toEntity(taskDto);

        taskRepository.save(task);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDto) {
        Task task = taskDTOConverter.toEntity(taskDto);
        task.setId(id);

        taskRepository.save(task);
        return taskDto;
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new TaskNotFoundException(id));
        task.setDeleted(true);
        task.setDeleteDate(LocalDateTime.now());

        taskRepository.save(task);
    }

    @Override
    public TaskDTO getTask(Long id) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return taskDTOConverter.toDto(task);
    }

    @Override
    public List<TaskDTO> getAllTask() {
        List<Task> taskList = taskRepository.getAllByIsDeletedFalse();

        return taskDTOConverter.toDtoList(taskList);

    }
}
