package com.epam.internship.service.impl;

import com.epam.internship.converter.TaskDTOConverter;
import com.epam.internship.dto.TaskDTO;
import com.epam.internship.entity.Task;
import com.epam.internship.entity.User;
import com.epam.internship.exception.TaskNotFoundException;
import com.epam.internship.repository.TaskRepository;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.epam.internship.utils.MessageUtils.user_notFound_message;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskDTOConverter taskDTOConverter;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public void createTask(String username, TaskDTO taskDto) {
        User user = userRepository.findByUsernameAndEnabledIsTrue(username)
                .orElseThrow(()-> new UsernameNotFoundException(user_notFound_message));

        Task task = taskDTOConverter.toEntity(taskDto);
        task.setUser(user);

        taskRepository.save(task);
    }

    @Override
    public void updateTask(Long id, String username, TaskDTO taskDto) {
        Task task = taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(id, username)
                .orElseThrow(()-> new TaskNotFoundException(id));

        Task newTask = taskDTOConverter.toEntity(taskDto);
        newTask.setCreateDate(task.getCreateDate());
        newTask.setDeleted(task.isDeleted());
        newTask.setDeleteDate(task.getDeleteDate());
        newTask.setId(id);
        newTask.setUser(task.getUser());

        taskRepository.save(newTask);
    }

    @Override
    public void deleteTask(Long id, String username) {
        Task task = taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(id, username)
                .orElseThrow(()-> new TaskNotFoundException(id));
        task.setDeleted(true);
        task.setDeleteDate(LocalDateTime.now());

        taskRepository.save(task);
    }

    @Override
    public TaskDTO getTask(Long id, String username) {
        Task task = taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(id, username)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return taskDTOConverter.toDto(task);
    }

    @Override
    public List<TaskDTO> getAllTask(String username) {
        List<Task> taskList = taskRepository.getAllByUser_UsernameAndIsDeletedFalse(username);

        return taskDTOConverter.toDtoList(taskList);
    }
}
