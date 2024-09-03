package com.todoList_API_v1.TodoAPI.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoList_API_v1.TodoAPI.entity.Task;
import com.todoList_API_v1.TodoAPI.entity.User;
import com.todoList_API_v1.TodoAPI.model.CreateTaskRequest;
import com.todoList_API_v1.TodoAPI.model.TaskResponse;
import com.todoList_API_v1.TodoAPI.model.UpdateTaskRequest;
import com.todoList_API_v1.TodoAPI.model.WebResponse;
import com.todoList_API_v1.TodoAPI.repository.TaskRepository;
import com.todoList_API_v1.TodoAPI.repository.UserRepository;
import com.todoList_API_v1.TodoAPI.security.BCrypt;
import java.util.List;

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

        // User user = new User();
        // user.setUsername("test");
        // user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        // user.setName("Test");
        // user.setToken("test");
        // user.setTokenExpiredAt(System.currentTimeMillis() + 1000000);
        // userRepository.save(user);
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
        
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000);
        userRepository.save(user);

        for (int i = 0; i < 5; i++){
                Task task = new Task();
                task.setId("test-"+i);
                task.setTask("taks" + i);
                task.setDescription("desc" + i);
                task.setDeadline("selasa, 12 januari 2003");
                task.setCompleted(false);
                task.setUser(user);
                taskRepository.save(task);
        }

        mockMvc.perform(
                get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test"))
                .andExpectAll(status().isOk()).andDo(result -> {
                    WebResponse<List<TaskResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                        assertNull(response.getErrors());
                        assertEquals(5, response.getData().size());
                });

    }

    // Test Success
    @Test
    void getTaskNotFound() throws Exception {

        mockMvc.perform(
                get("/api/tasks")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isNotFound()).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertNotNull(response.getErrors());
                });
    }

    // Test Success
    @Test
    void updateTaskNotFound() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000);
        userRepository.save(user);

        for (int i = 0; i < 5; i++){
                Task task = new Task();
                task.setId("test-"+i);
                task.setTask("taks" + i);
                task.setDescription("desc" + i);
                task.setDeadline("selasa, 12 januari 2003");
                task.setCompleted(false);
                task.setUser(user);
                taskRepository.save(task);
        }

        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setCompleted(true);
        request.setDeadline("selasa, 21 januari 2003");

        mockMvc.perform(
                patch("/api/tasks/test-10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(status().isNotFound()).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertNotNull(response.getErrors());
                });
    }

    // Test Success
    @Test
    void updateTaskSuccess() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000);
        userRepository.save(user);

        for (int i = 0; i < 5; i++){
                Task task = new Task();
                task.setId("test-"+i);
                task.setTask("taks" + i);
                task.setDescription("desc" + i);
                task.setDeadline("selasa, 12 januari 2003");
                task.setCompleted(false);
                task.setUser(user);
                taskRepository.save(task);
        }

        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setCompleted(true);
        request.setDeadline("selasa, 21 januari 2003");

        mockMvc.perform(
                patch("/api/tasks/test-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(status().isOk()).andDo(result -> {
                    WebResponse<TaskResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertNull(response.getErrors());
                    assertEquals(true, response.getData().isCompleted());
                    assertEquals("selasa, 21 januari 2003", response.getData().getDeadline());

                    Task taskDB = taskRepository.findById("test-1").orElse(null);
                    assertNotNull(taskDB);
                    assertEquals(true, taskDB.isCompleted());;

                });
    }

    
}
