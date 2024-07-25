package com.example.blogapp.models;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String _id;

    private String username;

    private String email;

    @DBRef
    private List<Post> postsList = new ArrayList<>();

    public String get_Id() {
        return _id;
    }

}