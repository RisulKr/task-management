package com.epam.internship.controller;

import com.epam.internship.dto.TaskDTO;
import com.epam.internship.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.epam.internship.utils.ControllerUtils.listErrors;

@RestController
@RequestMapping("/task")
@CrossOrigin
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create task", description = "Create task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task is created successfully"),
            @ApiResponse(responseCode = "400", description = "Task is invalid"),

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
    @Operation(summary = "Update task", description = "Update task by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task is updated successfully"),
            @ApiResponse(responseCode = "400", description = "Task is invalid"),
            @ApiResponse(responseCode = "404", description = "Task is not found")
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
            @ApiResponse(responseCode = "200", description = "Task is updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task is not found")
    })
    ResponseEntity<String> deleteTask(@PathVariable Long id, Principal principal){
        taskService.deleteTask(id, principal.getName());
        return ResponseEntity.ok("Task has been deleted");
    };

    @Operation(summary = "Get task", description = "Get task by given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task is found successfully"),
            @ApiResponse(responseCode = "404", description = "Task is not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<TaskDTO> getTask(@PathVariable Long id, Principal principal){
        TaskDTO taskDTO = taskService.getTask(id, principal.getName());

        return ResponseEntity.ok(taskDTO);
    };

    @Operation(summary = "Get all task", description = "Get all task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks is found successfully"),
    })
    @GetMapping
    ResponseEntity<List<TaskDTO>> getAllTask(Principal principal){
        List<TaskDTO> taskDTOList = taskService.getAllTask(principal.getName());

        return ResponseEntity.ok(taskDTOList);
    };
}
