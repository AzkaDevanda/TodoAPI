package com.todoList_API_v1.TodoAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.todoList_API_v1.TodoAPI.entity.User;
import com.todoList_API_v1.TodoAPI.model.CreateTaskRequest;
import com.todoList_API_v1.TodoAPI.model.TaskResponse;
import com.todoList_API_v1.TodoAPI.model.UpdateTaskRequest;
import com.todoList_API_v1.TodoAPI.model.WebResponse;
import com.todoList_API_v1.TodoAPI.service.TaskService;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(path = "api/tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TaskResponse> createTask(User user, @RequestBody CreateTaskRequest request) {
        TaskResponse response = taskService.createResponse(user, request);
        return WebResponse.<TaskResponse>builder().data(response).build();
    }

    @GetMapping(path = "api/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<TaskResponse>> getTask(User user) {
        List<TaskResponse> response = taskService.getTask(user);
        return WebResponse.<List<TaskResponse>>builder().data(response).build();
    }

    @PatchMapping(path = "api/tasks/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TaskResponse> updateTask(User user, @PathVariable String id,
            @RequestBody UpdateTaskRequest request) {
        TaskResponse response = taskService.updateTask(user, id, request);
        return WebResponse.<TaskResponse>builder().data(response).build();
    }

    @DeleteMapping(path = "api/tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteTask(User user, @PathVariable String id) {
        taskService.deleteTask(user, id);
        return WebResponse.<String>builder().data("Task deleted successfully").build();
    }
}
