package com.todoList_API_v1.TodoAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoList_API_v1.TodoAPI.entity.User;
import com.todoList_API_v1.TodoAPI.model.CreateTaskRequest;
import com.todoList_API_v1.TodoAPI.model.TaskResponse;
import com.todoList_API_v1.TodoAPI.model.WebResponse;
import com.todoList_API_v1.TodoAPI.service.TaskService;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(path = "api/tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TaskResponse> createTask(User user, @RequestBody CreateTaskRequest request) {
        TaskResponse response = taskService.createResponse(user, request);
        return WebResponse.<TaskResponse>builder().data(response).build();
    }
}
