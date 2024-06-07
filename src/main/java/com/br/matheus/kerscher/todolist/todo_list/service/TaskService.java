package com.br.matheus.kerscher.todolist.todo_list.service;

import com.br.matheus.kerscher.todolist.todo_list.exceptions.ConflictException;
import com.br.matheus.kerscher.todolist.todo_list.exceptions.EntityNotFoundException;
import com.br.matheus.kerscher.todolist.todo_list.model.dto.TaskRequest;
import com.br.matheus.kerscher.todolist.todo_list.model.dto.TaskResponse;
import com.br.matheus.kerscher.todolist.todo_list.model.entity.Task;
import com.br.matheus.kerscher.todolist.todo_list.model.enums.TaskStatus;
import com.br.matheus.kerscher.todolist.todo_list.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.br.matheus.kerscher.todolist.todo_list.utils.BrazilTime.findCurrentTime;

@Service
public class TaskService {

    @Autowired
    TaskRepository repository;

    public List<TaskResponse> findAll() {
        List<TaskResponse> tasks = new ArrayList<TaskResponse>();

        List<Task> taskList =  repository.findAll();

        if(taskList.isEmpty()) {
            throw new EntityNotFoundException("No tasks were found");
        }

        for(Task t : taskList) {
            TaskResponse item = new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.getStatus(), t.getCreatedDate());

            tasks.add(item);
        }

        return tasks;
    }

    public TaskResponse findTask(Long taskId) {
        Task task = repository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getCreatedDate());
    }

    public TaskResponse createTask(TaskRequest request) {
        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new ConflictException("Tasks can only be created on weekdays");
        }

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCreatedDate(findCurrentTime());
        task.setStatus(TaskStatus.PENDING);

        task = repository.save(task);

        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getCreatedDate());
    }

    public TaskResponse updateTask(Long taskId, TaskRequest request) {
        Task task = repository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if(!TaskStatus.PENDING.equals(task.getStatus())) {
            throw new ConflictException("Tasks can only be updated with pending status");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        task = repository.save(task);

        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getCreatedDate());
    }

    public TaskResponse updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = repository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        task.setStatus(status);

        task = repository.save(task);

        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getCreatedDate());
    }

    public void removeTask(Long taskId) {
        Task task = repository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if(!TaskStatus.PENDING.equals(task.getStatus())) {
            throw new ConflictException("Tasks can only be updated with pending status");
        }

        LocalDateTime currentDate = findCurrentTime();
        LocalDateTime creationDate = task.getCreatedDate();
        long daysBetween = ChronoUnit.DAYS.between(creationDate, currentDate);

        if (daysBetween <= 5) {
            throw new ConflictException("Tasks can only be deleted if its creation date is older than 5 days ago");
        }

        repository.delete(task);
    }
}
