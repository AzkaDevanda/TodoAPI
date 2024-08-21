package com.todoList_API_v1.TodoAPI.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTaskRequest {

    @NotBlank
    @Size(max = 100)
    private String task;

    @NotBlank
    @Size(max = 100)
    private String description;

    @NotBlank
    private String deadline;
}
