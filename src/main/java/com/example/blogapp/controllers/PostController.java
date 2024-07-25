package com.example.blogapp.controllers;

import java.util.List;
import com.example.blogapp.models.Post;
import com.example.blogapp.services.PostService;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = { "http://localhost:3000", "http://localhost:8080" })
@Api(tags = "Post Controller of blogapp-with-mongoDB")
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createPost(@RequestBody Post post, @PathVariable String userId) {
        try {
            return new ResponseEntity<>(postService.createPost(post, userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getAllPosts(@PathVariable String userId) {
        List<Post> posts = postService.getAllPosts(userId);
        return ResponseEntity.ok(posts);

        // Other endpoints for post management
    }

    @PutMapping("/{postId}/approve")
    public void approvePost(@PathVariable String postId) {
        postService.approvePost(postId);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> approvePost(@PathVariable String postId, @RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(postId, post));
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String userId, @PathVariable String postId) {
        return postService.deletePost(userId, postId);
    }
}