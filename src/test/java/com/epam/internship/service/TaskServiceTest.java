package com.epam.internship.service;

import com.epam.internship.converter.DTOConverter;
import com.epam.internship.dto.TaskDTO;
import com.epam.internship.dto.TaskSelectDTO;
import com.epam.internship.entity.Task;
import com.epam.internship.entity.User;
import com.epam.internship.enums.Priority;
import com.epam.internship.enums.Status;
import com.epam.internship.exception.TaskNotFoundException;
import com.epam.internship.repository.TaskRepository;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static com.epam.internship.utils.MessageUtils.user_notFound_message;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaskServiceTest {
    @Autowired
    private TaskServiceImpl taskService;

    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private DTOConverter<Task, TaskDTO> taskDTOConverter;
    @MockBean
    private DTOConverter<Task, TaskSelectDTO> taskSelectDTOConverter;

    private final String username = "tolek";
    private User user;
    private Task task;
    private TaskDTO taskDTO;
    private TaskSelectDTO taskSelectDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1)
                .username(username)
                .enabled(true)
                .build();

        task = Task.builder()
                .id(1L)
                .title("Sample Task")
                .content("Sample content")
                .priority(Priority.MEDIUM)
                .status(Status.IN_PROGRESS)
                .dueDate(LocalDateTime.now())
                .isFavourite(false)
                .user(user)
                .createDate(LocalDateTime.now())
                .build();

        taskDTO = TaskDTO.builder()
                .title("Updated Title")
                .content("Updated content")
                .priority(Priority.TOP)
                .status(Status.DONE)
                .dueDate("2022-02-12 22:00")
                .isFavourite(true)
                .build();

        taskSelectDTO = TaskSelectDTO.builder()
                .title("Sample Task")
                .content("Sample content")
                .priority(Priority.MEDIUM)
                .status(Status.IN_PROGRESS)
                .dueDate("2022-02-12 22:00")
                .isFavourite(false)
                .build();
    }

    @Test
    public void createTaskWithValidUser() {
        when(userRepository.findByUsernameAndEnabledIsTrue(username)).thenReturn(Optional.of(user));
        when(taskDTOConverter.toEntity(taskDTO)).thenReturn(new Task());

        taskService.createTask(username, taskDTO);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void createTaskWithInvalidUser() {
        String randomUsername = "Cyberthron";

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            taskService.createTask(randomUsername, taskDTO);
        });

        assertEquals(user_notFound_message, exception.getMessage());
    }

    @Test
    public void updateTaskWithValidIdAndUser() {
        Long taskId = 1L;

        when(taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(taskId, username)).thenReturn(Optional.of(task));
        when(taskDTOConverter.toEntity(taskDTO)).thenReturn(new Task());

        taskService.updateTask(taskId, username, taskDTO);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void updateTaskWithInvalidIdOrUser() {
        Long taskId = 1L;

        when(taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(taskId, username)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(taskId, username, taskDTO);
        });

        assertEquals(new TaskNotFoundException(taskId).getMessage(), exception.getMessage());
    }

    @Test
    public void deleteTaskWithValidIdAndUser() {
        Long taskId = 1L;

        when(taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(taskId, username)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskId, username);

        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void deleteTaskWithInvalidIdOrUser() {
        Long taskId = 1L;

        when(taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(taskId, username)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.deleteTask(taskId, username);
        });

        assertEquals(new TaskNotFoundException(taskId).getMessage(), exception.getMessage());
    }

    @Test
    public void getTaskWithValidIdAndUser() {
        Long taskId = 1L;

        when(taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(taskId, username)).thenReturn(Optional.of(task));
        when(taskSelectDTOConverter.toDto(task)).thenReturn(taskSelectDTO);

        TaskSelectDTO result = taskService.getTask(taskId, username);

        assertEquals(taskSelectDTO, result);
    }

    @Test
    public void getTaskWithInvalidIdOrUser() {
        Long taskId = 1L;

        when(taskRepository.findByIdAndUser_UsernameAndIsDeletedFalse(taskId, username)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTask(taskId, username);
        });

        assertEquals(new TaskNotFoundException(taskId).getMessage(), exception.getMessage());
    }

    @Test
    public void getAllTask() {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "dueDate");
        Page<Task> taskPage = new PageImpl<>(Collections.singletonList(task));

        when(taskRepository.getAllByUser_UsernameAndIsDeletedFalse(username, pageable)).thenReturn(taskPage);
        when(taskSelectDTOConverter.toDto(task)).thenReturn(taskSelectDTO);

        Page<TaskSelectDTO> result = taskService.getAllTask(username, 0, 10, "dueDate", "asc");

        assertEquals(1, result.getTotalElements());
        assertEquals(taskSelectDTO, result.getContent().get(0));
    }

    @Test
    public void getAllFavouriteTask() {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "dueDate");
        task.setFavourite(true);
        Page<Task> taskPage = new PageImpl<>(Collections.singletonList(task));

        when(taskRepository.getAllByUser_UsernameAndIsFavouriteTrueAndIsDeletedFalse(username, pageable)).thenReturn(taskPage);
        when(taskSelectDTOConverter.toDto(task)).thenReturn(taskSelectDTO);

        Page<TaskSelectDTO> result = taskService.getAllFavouriteTask(username, 0, 10, "dueDate", "asc");

        assertEquals(1, result.getTotalElements());
        assertEquals(taskSelectDTO, result.getContent().get(0));
    }

    @Test
    public void getAllTaskByStatus() {
        Status status = Status.IN_PROGRESS;
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "dueDate");
        Page<Task> taskPage = new PageImpl<>(Collections.singletonList(task));

        when(taskRepository.getAllByUser_UsernameAndStatusAndIsDeletedFalseOrderByDueDateAsc(username, status, pageable)).thenReturn(taskPage);
        when(taskSelectDTOConverter.toDto(task)).thenReturn(taskSelectDTO);

        Page<TaskSelectDTO> result = taskService.getAllTaskByStatus(username, status, 0, 10, "dueDate", "asc");

        assertEquals(1, result.getTotalElements());
        assertEquals(taskSelectDTO, result.getContent().get(0));
    }
}



