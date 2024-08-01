package com.epam.internship.controller;

import com.epam.internship.dto.TaskDTO;
import com.epam.internship.dto.TaskSelectDTO;
import com.epam.internship.enums.Status;
import com.epam.internship.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.epam.internship.utils.ControllerUtils.listErrors;

@RestController
@RequestMapping("/task")
@CrossOrigin
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class TaskController {
    public static final String TASK_IS_NOT_FOUND = "Task is not found";
    public static final String TASK_IS_UPDATED_SUCCESSFULLY = "Task is updated successfully";
    public static final String ERROR_IN_VALIDATION = "Task is invalid and has some error in validation";
    public static final String FOUND_SUCCESSFULLY = "Tasks is found successfully";
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create task", description = "Create operation for task. We need to provide requestBody for task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task is created successfully"),
            @ApiResponse(responseCode = "400", description = ERROR_IN_VALIDATION),

    })
    ResponseEntity<String> createTask(Principal principal,
                                      @RequestBody @Valid TaskDTO taskDto,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return listErrors(bindingResult);
        }
        taskService.createTask(principal.getName(), taskDto);

        return ResponseEntity.ok("Task has been created");
    };

    @PutMapping("/{id}")
    @Operation(summary = "Update task", description = "Update task by given as url id and modified task as request body")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = TASK_IS_UPDATED_SUCCESSFULLY),
            @ApiResponse(responseCode = "400", description = ERROR_IN_VALIDATION),
            @ApiResponse(responseCode = "404", description = TASK_IS_NOT_FOUND)
    })
    ResponseEntity<String> updateTask(@PathVariable Long id,
                                      Principal principal,
                                       @RequestBody @Valid TaskDTO taskDto,
                                       BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return listErrors(bindingResult);
        }

        taskService.updateTask(id, principal.getName(), taskDto);

        return ResponseEntity.ok("Task has been updated");
    };

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete task", description = "Delete task by given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task is deleted successfully"),
            @ApiResponse(responseCode = "404", description = TASK_IS_NOT_FOUND)
    })
    ResponseEntity<String> deleteTask(@PathVariable Long id, Principal principal){
        taskService.deleteTask(id, principal.getName());
        return ResponseEntity.ok("Task has been deleted");
    };

    @Operation(summary = "Get task", description = "Get task by given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task is found successfully"),
            @ApiResponse(responseCode = "404", description = TASK_IS_NOT_FOUND)
    })
    @GetMapping("/{id}")
    ResponseEntity<TaskSelectDTO> getTask(@PathVariable Long id, Principal principal){
        TaskSelectDTO taskDTO = taskService.getTask(id, principal.getName());

        return ResponseEntity.ok(taskDTO);
    };

    @Operation(summary = "Get all task", description = "Get all task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = FOUND_SUCCESSFULLY),
    })
    @GetMapping
    ResponseEntity<Page<TaskSelectDTO>> getAllTask(Principal principal,
                                                   @RequestParam(defaultValue = "0") int pageNo,
                                                   @RequestParam(defaultValue = "10") int pageSize,
                                                   @RequestParam(defaultValue = "dueDate") String sortBy,
                                                   @RequestParam(defaultValue = "desc") String direction){
        Page<TaskSelectDTO> taskDTOList = taskService.getAllTask(principal.getName(), pageNo, pageSize, sortBy, direction);

        return ResponseEntity.ok(taskDTOList);
    };

    @Operation(summary = "Get all task for board", description = "Get all task by status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = FOUND_SUCCESSFULLY),
    })
    @GetMapping("/status")
    ResponseEntity<Page<TaskSelectDTO>> getAllTaskForBoard(@RequestParam Status status,
                                                           Principal principal,
                                                           @RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "5") int pageSize,
                                                           @RequestParam(defaultValue = "dueDate") String sortBy,
                                                           @RequestParam(defaultValue = "desc") String direction){
        Page<TaskSelectDTO> taskDTOList = taskService.getAllTaskByStatus(principal.getName(), status, pageNo, pageSize,sortBy,direction);

        return ResponseEntity.ok(taskDTOList);
    }

    @Operation(summary = "Get all favourite tasks", description = "Favourite task list")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = FOUND_SUCCESSFULLY),
    })
    @GetMapping("/favourite")
    ResponseEntity<Page<TaskSelectDTO>> getAllFavouriteTask(Principal principal,
                                                           @RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "5") int pageSize,
                                                           @RequestParam(defaultValue = "dueDate") String sortBy,
                                                           @RequestParam(defaultValue = "desc") String direction){
        Page<TaskSelectDTO> taskDTOList = taskService.getAllFavouriteTask(principal.getName(),  pageNo, pageSize,sortBy,direction);

        return ResponseEntity.ok(taskDTOList);
    }

    @Operation(summary = "Get reminder", description = "Notification of item that gets count of task that due date is within next week")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = FOUND_SUCCESSFULLY),
    })
    @GetMapping("/notification")
    ResponseEntity<Integer> getNotification(Principal principal){
        int count = taskService.countTasksWithin7days(principal.getName());

        return ResponseEntity.ok(count);
    };
}
