package com.todoList_API_v1.TodoAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoList_API_v1.TodoAPI.entity.User;
import com.todoList_API_v1.TodoAPI.model.RegisterUserRequest;
import com.todoList_API_v1.TodoAPI.model.UserResponse;
import com.todoList_API_v1.TodoAPI.model.WebResponse;
import com.todoList_API_v1.TodoAPI.repository.UserRepository;
import com.todoList_API_v1.TodoAPI.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    // Test Succsess
    @Test
    void testRegisterUserBadRequest() throws Exception {
        RegisterUserRequest user = new RegisterUserRequest();
        user.setUsername("");
        user.setPassword("");
        user.setName("");

        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpectAll(status().isBadRequest()).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNotNull(response.getErrors());
                });
    }

    // Test Succsess
    @Test
    void testRegisterUserSuccess() throws Exception {
        RegisterUserRequest user = new RegisterUserRequest();
        user.setUsername("usernameTest");
        user.setPassword("rahasia");
        user.setName("nameTest");

        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpectAll(status().isOk()).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertEquals("User registered successfully", response.getData());
                });
    }

    // Test Succsess
    @Test
    void testRegisterUserDuplicate() throws Exception {
        User user = new User();
        user.setUsername("usernameTest");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setName("nameTest");
        userRepository.save(user);

        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("usernameTest");
        request.setPassword("rahasia");
        request.setName("nameTest");

        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpectAll(status().isBadRequest()).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNotNull(response.getErrors());
                });
    }

    // Test Succsess
    @Test
    void getUserUnauthorized() throws Exception {
        mockMvc.perform(
                get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isUnauthorized())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertNotNull(response.getErrors());
                });
    }

    // Test Succsess
    @Test
    void getUserTokenNotFound() throws Exception {
        mockMvc.perform(
                get("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "not found"))
                .andExpectAll(
                        status().isUnauthorized())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertNotNull(response.getErrors());
                });
    }

    // Test Succsess
    @Test
    void getUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setName("Test");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000L);
        userRepository.save(user);

        mockMvc.perform(
                get("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test"))
                .andExpectAll(
                        status().isOk())
                .andDo(result -> {
                    WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertNull(response.getErrors());
                    assertEquals("test", response.getData().getUsername());
                    assertEquals("Test", response.getData().getName());
                });
    }

}
