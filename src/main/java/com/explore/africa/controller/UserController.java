package com.explore.africa.controller;

import com.explore.africa.dto.CreateUser;
import com.explore.africa.dto.UserResponse;
import com.explore.africa.model.User;
import com.explore.africa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUser user){
        User use = service.create(user.name());

        UserResponse response = new UserResponse(
                use.getId(),
                use.getName()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = this.service.getAllUsers()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName()
                )).toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        User user = this.service.findById(id);
        UserResponse response = new UserResponse(user.getId(), user.getName());
        return ResponseEntity.ok(response);
    }
}
