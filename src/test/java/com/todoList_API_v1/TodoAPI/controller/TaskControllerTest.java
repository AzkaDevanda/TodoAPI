package com.todoList_API_v1.TodoAPI.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoList_API_v1.TodoAPI.entity.User;
import com.todoList_API_v1.TodoAPI.model.CreateTaskRequest;
import com.todoList_API_v1.TodoAPI.model.TaskResponse;
import com.todoList_API_v1.TodoAPI.model.WebResponse;
import com.todoList_API_v1.TodoAPI.repository.TaskRepository;
import com.todoList_API_v1.TodoAPI.repository.UserRepository;
import com.todoList_API_v1.TodoAPI.security.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000);
        userRepository.save(user);
    }

    // test Success
    @Test
    void createTaskBadRequest() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTask("");

        mockMvc.perform(
                post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(status().isBadRequest()).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertNotNull(response.getErrors());
                });

    }

    // Test Success
    @Test
    void createTaskSuccess() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTask("taks1");
        request.setDescription("desc1");
        request.setDeadline("selasa, 12 januari 2003");

        mockMvc.perform(
                post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(status().isOk()).andDo(result -> {
                    WebResponse<TaskResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertNull(response.getErrors());

                    assertEquals("taks1", response.getData().getTask());
                    assertEquals("desc1", response.getData().getDescription());
                    assertEquals("selasa, 12 januari 2003", response.getData().getDeadline());
                    assertEquals(false, response.getData().isCompleted());

                    assertTrue(taskRepository.findById(response.getData().getId()).isPresent());
                });

    }
}
