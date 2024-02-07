package com.aymen.realestate.controller;

import com.aymen.realestate.dto.*;
import com.aymen.realestate.model.User;
import com.aymen.realestate.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody UserSignupRequest request) {
        try {
            authService.signUp(request.getUsername(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new ApiResponse(true, null, null , "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, null, null,"Error during user registration"));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signIn(@RequestBody UserSigninRequest request, HttpServletResponse response) {
        SignInResponse signInResponse = authService.signIn(request.getEmail(), request.getPassword());

        if (signInResponse.success()) {
            response.addCookie(signInResponse.cookie());
            return ResponseEntity.ok(new ApiResponse(true, signInResponse.user(), null, "Authentication successful"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, null, null, signInResponse.message()));
        }
    }

    @PostMapping("/googleAuth")
    public ResponseEntity<ApiResponse> googleAuth(@RequestBody GoogleAuthRequest googleAuthRequest, HttpServletResponse response) {


        try {
            SignInResponse signInResponse = authService.googleAuth(
                    googleAuthRequest.getEmail(),
                    googleAuthRequest.getUsername(),
                    googleAuthRequest.getAvatar()
            );

            if (signInResponse.success()) {
                response.addCookie(signInResponse.cookie());
                return ResponseEntity.ok(new ApiResponse(true, signInResponse.user(), null, "Authentication successful"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, null, null, signInResponse.message()));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/signout")
    public ResponseEntity<ApiResponse> signOut(HttpServletResponse response) {
        try {
            authService.signOut(response);
            return ResponseEntity.ok(new ApiResponse(true, null, null , "Sign-out successful!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null, "Error during sign-out"));
        }
    }
}
