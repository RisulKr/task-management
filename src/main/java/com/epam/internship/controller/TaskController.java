package com.epam.internship.controller;

import com.epam.internship.dto.TaskDTO;
import com.epam.internship.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.internship.utils.ControllerUtils.listErrors;

@RestController
@RequestMapping("/task")
@CrossOrigin
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create task", description = "Create task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task is created successfully"),
            @ApiResponse(responseCode = "400", description = "Task is invalid")
    })
    ResponseEntity<String> createTask(@RequestBody @Valid TaskDTO taskDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return listErrors(bindingResult);
        }
        taskService.createTask(taskDto);

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
                                       @RequestBody @Valid TaskDTO taskDto,
                                       BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return listErrors(bindingResult);
        }

        taskService.updateTask(id, taskDto);

        return ResponseEntity.ok("Task has been updated");
    };

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete task", description = "Delete task by given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task is updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task is not found")
    })
    ResponseEntity<String> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task has been deleted");
    };

    @Operation(summary = "Get task", description = "Get task by given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task is found successfully"),
            @ApiResponse(responseCode = "404", description = "Task is not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<TaskDTO> getTask(@PathVariable Long id){
        TaskDTO taskDTO = taskService.getTask(id);

        return ResponseEntity.ok(taskDTO);
    };

    @Operation(summary = "Get all task", description = "Get all task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks is found successfully"),
    })
    @GetMapping
    ResponseEntity<List<TaskDTO>> getAllTask(){
        List<TaskDTO> taskDTOList = taskService.getAllTask();

        return ResponseEntity.ok(taskDTOList);
    };
}
