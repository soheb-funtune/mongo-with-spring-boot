Tutorial: Building a Simple Blog Application with Spring Boot

Introduction:
We're going to build a blog application where users can create posts. Think of it like a simple version of Twitter or Facebook posts. We'll use Spring Boot, which is a tool that helps us create web applications easily.

Step 1: Setting Up the Project

Why: We need a place to put all our code and tell the computer what tools we're using.

How:

1. Go to https://start.spring.io/
2. Choose:
   - Project: Maven
   - Language: Java
   - Spring Boot: (latest stable version)
   - Group: com.example
   - Artifact: blogapp
3. Add Dependencies: Spring Web, Spring Data JPA, H2 Database
4. Click "Generate" and unzip the downloaded file

This creates a folder structure and a file called pom.xml that lists all the tools we'll use.

Test: Open the project in your IDE. If it loads without errors, you're good!

Step 2: Creating the User Model

Why: We need to tell the computer what a "user" looks like in our app.

How: Create a new file called User.java in src/main/java/com/example/blogapp/models and add this code:

```java
package com.example.blogapp.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String email;

    // Constructors, getters, and setters
}
```

This tells the computer that a user has an ID, a username, and an email.

Test: Try to compile your project. If there are no errors, you've defined the User correctly!

Step 3: Creating the Post Model

Why: We need to define what a "post" looks like in our app.

How: Create a new file called Post.java in the same folder and add:

```java
package com.example.blogapp.models;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    private String title;
    private String content;
    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors, getters, and setters
}
```

This tells the computer that a post has an ID, title, content, approval status, and is associated with a user.

Test: Again, try to compile. No errors means you've defined Post correctly!

Step 4: Creating Repositories

Why: We need a way to save and retrieve users and posts from our database.

How: Create two new interfaces:

UserRepository.java:

```java
package com.example.blogapp.repositories;

import com.example.blogapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
```

PostRepository.java:

```java
package com.example.blogapp.repositories;

import com.example.blogapp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
```

These interfaces tell Spring how to interact with our database for Users and Posts.

Test: Compile your project. No errors? Great! The repositories are set up correctly.

Step 5: Creating Services

Why: We need to define the actions our app can perform, like creating users and posts.

How: Create two new classes:

UserService.java:

```java
package com.example.blogapp.services;

import com.example.blogapp.models.User;
import com.example.blogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
```

PostService.java:

```java
package com.example.blogapp.services;

import com.example.blogapp.models.Post;
import com.example.blogapp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post approvePost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setApproved(true);
        return postRepository.save(post);
    }
}
```

These services define the actions our app can perform.

Test: Compile your project. No errors means your services are set up correctly!

Step 6: Creating Controllers

Why: We need to create endpoints that users can interact with to use our app.

How: Create two new classes:

UserController.java:

```java
package com.example.blogapp.controllers;

import com.example.blogapp.models.User;
import com.example.blogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
```

PostController.java:

```java
package com.example.blogapp.controllers;

import com.example.blogapp.models.Post;
import com.example.blogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/{postId}/approve")
    public Post approvePost(@PathVariable int postId) {
        return postService.approvePost(postId);
    }
}
```

These controllers create the endpoints that users can interact with.

Test: Start your Spring Boot application. If it starts without errors, your controllers are set up correctly!

Step 7: Testing the Application

Now that we've built our app, let's test it!

1. Create a user:

   - Use Postman or any API testing tool
   - Send a POST request to http://localhost:8080/api/users
   - With body:
     ```json
     {
       "username": "testuser",
       "email": "test@example.com"
     }
     ```
   - You should get a response with the created user and an ID

2. Create a post:

   - Send a POST request to http://localhost:8080/api/posts
   - With body:
     ```json
     {
       "title": "My First Post",
       "content": "Hello, World!",
       "user": {
         "userId": 1
       }
     }
     ```
   - You should get a response with the created post, including its ID and approved status (false)

3. Approve the post:
   - Send a PUT request to http://localhost:8080/api/posts/1/approve
   - You should get a response with the updated post, now with approved status true

Congratulations! You've built and tested a simple blog application. This app allows creating users, creating posts, and approving posts. Each step we took was about telling the computer a little more about what we want our app to do, from defining what users and posts look like, to specifying how to store them, to creating ways for people to interact with our app.
