package com.todoList_API_v1.TodoAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoList_API_v1.TodoAPI.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
