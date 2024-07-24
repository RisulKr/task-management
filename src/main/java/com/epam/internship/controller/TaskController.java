package com.epam.internship.controller;

import com.epam.internship.dto.TaskDTO;
import com.epam.internship.service.TaskService;
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
    ResponseEntity<String> createTask(@RequestBody @Valid TaskDTO taskDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return listErrors(bindingResult);
        }
        taskService.createTask(taskDto);

        return ResponseEntity.ok("Task has been created");
    };

    @PutMapping("/{id}")
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
    ResponseEntity<String> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task has been deleted");
    };

    @GetMapping("/{id}")
    ResponseEntity<TaskDTO> getTask(@PathVariable Long id){
        TaskDTO taskDTO = taskService.getTask(id);

        return ResponseEntity.ok(taskDTO);
    };

    @GetMapping
    ResponseEntity<List<TaskDTO>> getAllTask(){
        List<TaskDTO> taskDTOList = taskService.getAllTask();

        return ResponseEntity.ok(taskDTOList);
    };
}
