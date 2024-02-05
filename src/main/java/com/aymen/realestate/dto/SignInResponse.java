package com.aymen.realestate.dto;

import com.aymen.realestate.model.User;
import jakarta.servlet.http.Cookie;

public record SignInResponse(boolean success, String message, User user, Cookie cookie) {

}
