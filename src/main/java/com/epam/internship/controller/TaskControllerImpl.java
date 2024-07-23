//package com.epam.internship.controller;
//
//import com.epam.internship.dto.TaskDto;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/task")
//
//public class TaskControllerImpl {
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Create Task")
//    ResponseEntity<String> createTask(@RequestBody TaskDto taskDto){
//        return null;
//    }
//
//    @PutMapping("/{id}")
//    @Operation( summary = "Update Task")
//    ResponseEntity<TaskDto> updateTask(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
//        return null;
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @Operation(summary = "Delete Task by id")
//   // @ApiImplicitParam(name = "id", value = "TaskEntity id", paramType = "path", required = true)
//    ResponseEntity<String> deleteTask(@PathVariable Integer id){
//        return null;
//    };
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Get Task by id")
//    //@ApiImplicitParam(name = "id", value = "TaskEntity id", paramType = "path", required = true)
//    ResponseEntity<TaskDto> getTask(@PathVariable Integer id){
//        return null;
//    }
//
//    @GetMapping
//    @Operation(summary = "Get list of all Tasks")
//    ResponseEntity<List<TaskDto>> getAllTask(){
//        return null;
//    }
//}
