package com.todoList_API_v1.TodoAPI.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateTaskRequest {

    @Size(max = 100)
    private String task;

    @Size(max = 100)
    private String description;

    private boolean completed;

    private String deadline;
}
