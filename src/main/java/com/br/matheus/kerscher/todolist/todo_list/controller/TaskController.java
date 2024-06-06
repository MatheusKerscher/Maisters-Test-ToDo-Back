package com.br.matheus.kerscher.todolist.todo_list.controller;

import com.br.matheus.kerscher.todolist.todo_list.model.dto.TaskRequest;
import com.br.matheus.kerscher.todolist.todo_list.model.dto.TaskResponse;
import com.br.matheus.kerscher.todolist.todo_list.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{taskid}")
    public ResponseEntity<TaskResponse> findTask(@PathVariable Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findTask(taskId));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTask(request));
    }
}
