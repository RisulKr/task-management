package com.epam.internship.controller;

import com.epam.internship.dto.TaskDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Api(tags = "Task Endpoints")
public class TaskControllerImpl {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create Task")
    ResponseEntity<String> createTask(@RequestBody TaskDto taskDto){
        return null;
    }

    @PutMapping("/{id}")
    @ApiOperation("Update Task")
    ResponseEntity<TaskDto> updateTask(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete Task by id")
    @ApiImplicitParam(name = "id", value = "TaskEntity id", paramType = "path", required = true)
    ResponseEntity<String> deleteTask(@PathVariable Integer id){
        return null;
    };

    @GetMapping("/{id}")
    @ApiOperation("Get Task by id")
    @ApiImplicitParam(name = "id", value = "TaskEntity id", paramType = "path", required = true)
    ResponseEntity<TaskDto> getTask(@PathVariable Integer id){
        return null;
    }

    @GetMapping
    @ApiOperation("Get list of all Tasks")
    ResponseEntity<List<TaskDto>> getAllTask(){
        return null;
    }
}
