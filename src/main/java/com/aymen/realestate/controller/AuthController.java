package com.aymen.realestate.controller;

import com.aymen.realestate.dto.AuthenticationResult;
import com.aymen.realestate.dto.UserSigninRequest;
import com.aymen.realestate.dto.UserSignupRequest;
import com.aymen.realestate.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignupRequest request) {
        if (request == null){
            System.out.println("request is null");
        }
        else {
            System.out.println("request is not null");
            System.out.println("request name " + request.getUsername());
            System.out.println("request email " +  request.getEmail());
            System.out.println("request password " + request.getPassword());

        }
        try {
            authService.signUp(request.getUsername(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during user registration");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserSigninRequest request, HttpServletResponse response) {
        AuthenticationResult result = authService.signIn(request.getEmail(), request.getPassword());

        if (result.isSuccess()) {
            response.addCookie(result.getCookie());

            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("success", true);
            successResponse.put("message", "Authentication successful");
            successResponse.put("user", result.getUser());

            return ResponseEntity.status(HttpStatus.OK).body(successResponse);

        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", result.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

    }

    @GetMapping("/signout")
    public ResponseEntity<String> signOut(HttpServletResponse response) {
        authService.signOut(response);
        return ResponseEntity.ok().body("Sign-out successful!");
    }



}
