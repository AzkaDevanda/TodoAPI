package com.todoList_API_v1.TodoAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoList_API_v1.TodoAPI.entity.User;
import com.todoList_API_v1.TodoAPI.model.LoginUserRequest;
import com.todoList_API_v1.TodoAPI.model.TokenResponse;
import com.todoList_API_v1.TodoAPI.model.WebResponse;
import com.todoList_API_v1.TodoAPI.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "api/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> loginUser(@RequestBody LoginUserRequest request) {
        TokenResponse response = authService.loginUser(request);
        return WebResponse.<TokenResponse>builder().data(response).build();
    }

    @DeleteMapping(path = "api/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logoutUser(User user) {
        authService.logOut(user);
        return WebResponse.<String>builder().data("User logged out successfully").build();
    }
    
}
