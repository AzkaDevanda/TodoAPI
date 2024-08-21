package com.todoList_API_v1.TodoAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {

    private String id;

    private String task;

    private String description;

    private boolean completed;

    private String deadline;
}
