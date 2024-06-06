package com.br.matheus.kerscher.todolist.todo_list.repository;

import com.br.matheus.kerscher.todolist.todo_list.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
