package com.example.blogapp.models;

import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Entity
// @Table(name = "posts")
@Document(collection = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String _id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private boolean approved;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "user_id")
    // private User user;

}