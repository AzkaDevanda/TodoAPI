package com.todoList_API_v1.TodoAPI.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import com.todoList_API_v1.TodoAPI.entity.Task;
import com.todoList_API_v1.TodoAPI.entity.User;
import com.todoList_API_v1.TodoAPI.model.CreateTaskRequest;
import com.todoList_API_v1.TodoAPI.model.TaskResponse;
import com.todoList_API_v1.TodoAPI.repository.TaskRepository;

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

    @Transactional(readOnly = true)
    public List<TaskResponse> getTask(User user) {
        List<Task> task = taskRepository.findAllByUser(user);
        return task.stream().map(this::createResponse).toList();
    }
}
