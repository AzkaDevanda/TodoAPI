package com.todoList_API_v1.TodoAPI.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoList_API_v1.TodoAPI.entity.Task;
import com.todoList_API_v1.TodoAPI.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findAllByUser(User user);

    Optional<Task> findFirstByUserAndId (User user, String taskId);

}
