package com.epam.internship.service.impl;

import com.epam.internship.converter.DTOConverter;
import com.epam.internship.dto.TaskDTO;
import com.epam.internship.dto.TaskSelectDTO;
import com.epam.internship.entity.Task;
import com.epam.internship.entity.User;
import com.epam.internship.enums.Status;
import com.epam.internship.exception.TaskNotFoundException;
import com.epam.internship.repository.TaskRepository;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.epam.internship.utils.MessageUtils.user_notFound_message;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final DTOConverter<Task,TaskDTO> taskDTOConverter;
    private final DTOConverter<Task, TaskSelectDTO> taskSelectDTOConverter;
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
    public TaskSelectDTO getTask(Long id, String username) {
        Task task = taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(id, username)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return taskSelectDTOConverter.toDto(task);
    }

    @Override
    public Page<TaskSelectDTO> getAllTask(String username, int pageNo, int pageSize, String sortBy, String direction) {
        Pageable pageable = getPageable(pageNo, pageSize, sortBy, direction);
        Page<Task> taskList = taskRepository.getAllByUser_UsernameAndIsDeletedFalse(username, pageable);

        return taskList.map(taskSelectDTOConverter::toDto);
    }

    @Override
    public Page<TaskSelectDTO> getAllFavouriteTask(String username, int pageNo, int pageSize, String sortBy, String direction) {
        Pageable pageable = getPageable(pageNo, pageSize, sortBy, direction);
        Page<Task> taskList = taskRepository.getAllByUser_UsernameAndIsFavouriteTrueAndIsDeletedFalse(username, pageable);

        return taskList.map(taskSelectDTOConverter::toDto);
    }

    @Override
    public Page<TaskSelectDTO> getAllTaskByStatus(String username, Status status, int pageNo, int pageSize, String sortBy, String direction) {
        Pageable pageable = getPageable(pageNo, pageSize, sortBy, direction);
        Page<Task> taskList = taskRepository.getAllByUser_UsernameAndStatusAndIsDeletedFalseOrderByDueDateAsc(username, status, pageable);

        return taskList.map(taskSelectDTOConverter::toDto);
    }

    @Override
    public int countTasksWithin7days(String userName) {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(6);

        return taskRepository.countAllByDueDateBetweenAndUser_UsernameAndIsDeletedFalse(startDate, endDate, userName);
    }

    private static PageRequest getPageable(int pageNo, int pageSize, String sortBy, String direction) {
        return PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(direction), sortBy);
    }
}
