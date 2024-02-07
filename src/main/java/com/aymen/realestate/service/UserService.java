package com.aymen.realestate.service;

import com.aymen.realestate.dto.ApiResponse;
import com.aymen.realestate.dto.UserUpdateRequest;
import com.aymen.realestate.model.Listing;
import com.aymen.realestate.model.User;
import com.aymen.realestate.repository.ListingRepository;
import com.aymen.realestate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListingRepository listingRepository;

    public User updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<Listing> getUserListings(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return listingRepository.findByUserId(user.getId().toString());
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }



}