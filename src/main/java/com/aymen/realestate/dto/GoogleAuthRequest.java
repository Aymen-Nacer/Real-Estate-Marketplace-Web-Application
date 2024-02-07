package com.aymen.realestate.dto;

public class GoogleAuthRequest {
    private String email;
    private String username;
    private String avatar;


    public GoogleAuthRequest() {
    }

    public GoogleAuthRequest(String email, String username, String avatar) {
        this.email = email;
        this.username = username;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
