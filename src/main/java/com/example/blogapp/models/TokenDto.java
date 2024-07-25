
package com.example.blogapp.models;

public class TokenDto {

    private String token;

    public TokenDto(String t) {
        this.token = t;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenDto{" +
                "token='" + token + '\'' +
                '}';
    }

}