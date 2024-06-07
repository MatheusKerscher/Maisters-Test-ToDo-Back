package com.br.matheus.kerscher.todolist.todo_list.controller;

import com.br.matheus.kerscher.todolist.todo_list.model.dto.TaskRequest;
import com.br.matheus.kerscher.todolist.todo_list.model.dto.TaskResponse;
import com.br.matheus.kerscher.todolist.todo_list.model.enums.TaskStatus;
import com.br.matheus.kerscher.todolist.todo_list.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> findTask(@PathVariable Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findTask(taskId));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTask(request));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> editTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequest request) {
       return ResponseEntity.status(HttpStatus.OK).body(service.updateTask(taskId, request));
    }

    @PutMapping("/status/{taskId}")
    public ResponseEntity<TaskResponse> editTaskStatus(@PathVariable Long taskId, @Valid @RequestBody TaskStatus status) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateTaskStatus(taskId, status));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        service.removeTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
