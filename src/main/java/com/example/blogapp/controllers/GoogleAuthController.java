
package com.example.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.models.TokenDto;
import com.example.blogapp.services.GoogleAuthService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = { "http://localhost:3000", "http://localhost:8081" })
@Api(tags = "Auth Controller of blogapp-with-mongoDB")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @PostMapping("/google_login")
    public ResponseEntity<?> authenticateGoogle(@RequestBody TokenDto tokenDto) {
        String acceessToken = tokenDto.getToken();
        System.out.println("acceessToken : " + acceessToken + " = " + tokenDto);
        if (acceessToken == null || acceessToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Access token is null or empty.");
        }
        try {
            return ResponseEntity.ok(googleAuthService.authenticateGoogleToken(acceessToken));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}