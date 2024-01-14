package com.aymen.realestate.controller;

import com.aymen.realestate.dto.UserSigninRequest;
import com.aymen.realestate.dto.UserSignupRequest;
import com.aymen.realestate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignupRequest request) {
        try {
            authService.signUp(request.getUsername(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during user registration");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserSigninRequest request) {
        try {
            authService.signIn(request.getEmail(), request.getPassword());
            return ResponseEntity.ok("User authenticated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/signout")
    public ResponseEntity<String> signOut() {
        authService.signOut();
        return ResponseEntity.ok("User signed out successfully");
    }




}
