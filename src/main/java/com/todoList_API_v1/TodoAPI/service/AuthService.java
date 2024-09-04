package com.todoList_API_v1.TodoAPI.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.todoList_API_v1.TodoAPI.entity.User;
import com.todoList_API_v1.TodoAPI.model.LoginUserRequest;
import com.todoList_API_v1.TodoAPI.model.TokenResponse;
import com.todoList_API_v1.TodoAPI.repository.UserRepository;
import com.todoList_API_v1.TodoAPI.security.BCrypt;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validation;

    @Transactional
    public TokenResponse loginUser(LoginUserRequest request) {
        validation.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password Wrong"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            // succsess
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30Days());
            userRepository.save(user);

            return TokenResponse.builder().token(user.getToken()).tokenExpiredAt(user.getTokenExpiredAt()).build();
        } else {
            // fail
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password Wrong");
        }
    }

    private Long next30Days() {
        return System.currentTimeMillis() + (100000000000L);
    }

    @Transactional
    public void logOut(User user){
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }
}
