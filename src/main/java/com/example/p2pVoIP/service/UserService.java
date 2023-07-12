package com.example.p2pVoIP.service;

import com.example.p2pVoIP.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(Long id);
    Optional<User> findByUsername(String username);
}
