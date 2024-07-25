package com.example.blogapp.controllers;

import com.example.blogapp.models.User;
import com.example.blogapp.services.UserService;

import io.swagger.annotations.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = { "http://localhost:3000", "http://localhost:8081" })
@Api(tags = "User Controller of blogapp-with-mongoDB")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User userCreated = userService.createUser(user);
            if (userCreated != null) {

                return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
            } else {
                throw new Error("User Not Fount");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (!users.isEmpty()) {
                return new ResponseEntity<>(users, HttpStatus.OK);

            } else {
                throw new Error("0 users ");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getSingleUser(@PathVariable String userId) {
        try {
            return new ResponseEntity<>(userService.getSingleUser(userId),
                    HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId, @RequestBody User user) {
        try {

            return new ResponseEntity<>(userService.updateUser(userId, user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);

        }
    }

    // Other endpoints for user management
}