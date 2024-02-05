package com.aymen.realestate.dto;

import com.aymen.realestate.model.Listing;
import com.aymen.realestate.model.User;
import jakarta.servlet.http.Cookie;
import java.util.List;

public class ApiResponse {

    private final boolean success;
    private final User user;
    private final List<Listing> listings;
    private final String message;


    public ApiResponse(boolean success, User user, List<Listing> listings, String message) {
        this.success = success;
        this.user = user;
        this.listings = listings;
        this.message = message;

    }

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public String getMessage() {
        return message;
    }


}
