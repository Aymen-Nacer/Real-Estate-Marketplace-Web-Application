package com.aymen.realestate.controller;

import com.aymen.realestate.config.JwtTokenProvider;
import com.aymen.realestate.dto.ApiResponse;
import com.aymen.realestate.dto.UserUpdateRequest;
import com.aymen.realestate.model.Listing;
import com.aymen.realestate.model.User;
import com.aymen.realestate.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id,
                                        @RequestBody UserUpdateRequest request,
                                        @CookieValue(name = "access_token") String accessTokenCookie) {
        try {
            Jws<Claims> claimsJws = jwtTokenProvider.validateToken(accessTokenCookie);
            User updatedUser = userService.updateUser(id, request);
            return ResponseEntity.ok(new ApiResponse(true, updatedUser, null ,"User updated successfully"));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, null, null ,"Token is not valid"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null ,"Error updating user"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id,
                                        @CookieValue(name = "access_token") String accessTokenCookie) {
        try {
            Jws<Claims> claimsJws = jwtTokenProvider.validateToken(accessTokenCookie);
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse(true, null, null, "User deleted successfully"));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, null,null, "Token is not valid"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null,"Error deleting user"));
        }
    }

    @GetMapping("/listings/{id}")
    public ResponseEntity<ApiResponse> getUserListings(@PathVariable Long id) {
        try {
            List<Listing> userlistings = userService.getUserListings(id);
            return ResponseEntity.ok(new ApiResponse(true, null,  userlistings, "User listings fetched successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null, "Error fetching user listings"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long id,
                                     @CookieValue(name = "access_token") String accessTokenCookie) {
        try {
            Jws<Claims> claimsJws = jwtTokenProvider.validateToken(accessTokenCookie);
            User user = userService.getUser(id);
            return ResponseEntity.ok(new ApiResponse(true, user, null ,"User fetched successfully"));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, null, null,  "Token is not valid"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null,  "Error fetching user"));
        }
    }
}
