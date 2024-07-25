package com.example.blogapp.services;

import com.example.blogapp.models.User;
import com.example.blogapp.repositories.UserRepository;

// import org.hibernate.mapping.List;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // User oldUser = userRepository.findByEmail(user.getEmail());
        // if (oldUser != null) {

        // HashMap<String, Object> map = new HashMap<>();
        // map.put("msg", "user is already Present with This Email ");
        // map.put("status", false);

        // return new ResponseEntity<>(new ErrorRespo("User already exists with email: "
        // + user.getEmail()),
        // HttpStatus.CONFLICT);
        // } else {

        return userRepository.save(user);
        // }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getSingleUser(String userId) {
        return userRepository.findById(userId).orElseThrow(null);
    }

    public User updateUser(String userId, User user) {
        User oldUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        oldUser.setEmail(user.getEmail());
        oldUser.setUsername(user.getUsername());
        return userRepository.save(oldUser);
    }

    public ResponseEntity<?> deleteUser(String userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("msg", "User Is Deleted ");
        map.put("userId", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userRepository.delete(user);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // Other methods for user management
}