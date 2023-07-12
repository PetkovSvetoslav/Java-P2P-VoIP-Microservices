package com.example.p2pVoIP.controller;


import com.example.p2pVoIP.model.User;
import com.example.p2pVoIP.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userServiceImpl.findByUsername(currentUserName)
                .orElseThrow(() -> new RuntimeException("User with username " + currentUserName + " not found"));
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/selected")
    public ResponseEntity<User> getSelectedUser(HttpSession session) {
        Long selectedUserId = (Long) session.getAttribute("selectedUserId");
        User selectedUser = userServiceImpl.getUserById(selectedUserId)
                .orElseThrow(() -> new RuntimeException("User with id " + selectedUserId + " not found"));
        return ResponseEntity.ok(selectedUser);
    }
}
