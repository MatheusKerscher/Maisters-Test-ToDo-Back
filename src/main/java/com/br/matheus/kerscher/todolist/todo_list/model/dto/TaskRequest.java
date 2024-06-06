package com.br.matheus.kerscher.todolist.todo_list.model.dto;

import com.br.matheus.kerscher.todolist.todo_list.model.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskRequest {
    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Description is required")
    private String description;
}
