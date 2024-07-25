package com.example.blogapp.services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.blogapp.models.Post;
import com.example.blogapp.models.User;
import com.example.blogapp.repositories.PostRepository;
import com.example.blogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createPost(Post post, String userId) {

        User user = userService.getSingleUser(userId);
        if (user != null) {
            Post savedPost = postRepository.save(post);
            user.getPostsList().add(savedPost);
            userService.createUser(user);

            return new ResponseEntity<>(savedPost, HttpStatus.OK);
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("Msg", "User is Not Present to Add postId into postsList of user");
            map.put("userId", userId);
            return new ResponseEntity<>(map, HttpStatus.OK);

        }

    }

    public void approvePost(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        post.setApproved(true);
        postRepository.save(post);
    }

    public Post updatePost(String postId, Post post) {
        Post oldPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        oldPost.setContent(post.getContent());
        oldPost.setTitle(post.getTitle());

        return postRepository.save(post);
    }

    public List<Post> getAllPosts(String userId) {
        return postRepository.findAll();
    }

    public ResponseEntity<?> deletePost(String userId, String postId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("postId", postId);
        map.put("msg", "post is Deleted Successfully ");
        try {
            User user = userService.getSingleUser(userId);
            user.getPostsList().removeIf(item -> item.get_id().equals(postId));
            userService.createUser(user);
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
            postRepository.delete(post);
            return ResponseEntity.ok(map);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Other methods for post management
}