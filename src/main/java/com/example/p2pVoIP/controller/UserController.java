package com.example.p2pVoIP.controller;


import com.example.p2pVoIP.model.User;
import com.example.p2pVoIP.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userServiceImpl.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userServiceImpl.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        return ResponseEntity.ok(user);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServiceImpl.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userServiceImpl.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}