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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.br.matheus.kerscher.todolist.todo_list.utils.BrazilTime.findCurrentTime;

@Service
public class TaskService {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    TaskRepository repository;

    public List<TaskResponse> findAll() {
        List<TaskResponse> tasks = new ArrayList<TaskResponse>();

        List<Task> taskList =  repository.findAll();

        if(taskList.isEmpty()) {
            throw new EntityNotFoundException("Tasks not found");
        }

        for(Task t : taskList) {
            TaskResponse item = modelMapper.map(t, TaskResponse.class);
            tasks.add(item);
        }

        return tasks;
    }

    public TaskResponse findTask(Long taskId) {
        Task task = repository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        return modelMapper.map(task, TaskResponse.class);
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

        return modelMapper.map(task, TaskResponse.class);
    }
}
