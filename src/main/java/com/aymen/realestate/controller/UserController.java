package com.aymen.realestate.controller;


import com.aymen.realestate.model.Listing;
import com.aymen.realestate.model.User;
import com.aymen.realestate.dto.UserUpdateRequest;
import com.aymen.realestate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/listings/{id}")
    public ResponseEntity<List<Listing>> getUserListings(@PathVariable Long id) {
        List<Listing> userlistings = userService.getUserListings(id);
        return ResponseEntity.ok(userlistings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        System.out.println("#########################");
        System.out.println("id received is  "  + id );
        System.out.println("#########################");

        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }


}