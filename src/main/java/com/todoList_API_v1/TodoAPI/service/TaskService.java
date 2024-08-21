package com.todoList_API_v1.TodoAPI.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoList_API_v1.TodoAPI.entity.Task;
import com.todoList_API_v1.TodoAPI.entity.User;
import com.todoList_API_v1.TodoAPI.model.CreateTaskRequest;
import com.todoList_API_v1.TodoAPI.model.TaskResponse;
import com.todoList_API_v1.TodoAPI.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private ValidationService validation;

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public TaskResponse createResponse(User user, CreateTaskRequest request) {
        validation.validate(request);

        Task task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setTask(request.getTask());
        task.setDescription(request.getDescription());
        task.setCompleted(false);
        task.setDeadline(request.getDeadline());
        task.setUser(user);

        taskRepository.save(task);

        return createResponse(task);
    }

    private TaskResponse createResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .task(task.getTask())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .deadline(task.getDeadline())
                .build();
    }
}
