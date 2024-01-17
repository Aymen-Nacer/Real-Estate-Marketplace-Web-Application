package com.aymen.realestate.dto;

import com.aymen.realestate.model.User;
import jakarta.servlet.http.Cookie;

public class AuthenticationResult {

    private final boolean success;
    private final User user;
    private final String token;
    private final String message;



    private final Cookie cookie;


    public AuthenticationResult(boolean success, User user, String token, String message, Cookie cookie) {
        this.success = success;
        this.user = user;
        this.token = token;
        this.message = message;
        this.cookie = cookie;

    }

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
