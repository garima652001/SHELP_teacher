package com.users.shelp_teacher.Response;

public class LoginResponse {
    String message,token,username,userId;

    public LoginResponse(String message, String token, String username, String userId) {
        this.message = message;
        this.token = token;
        this.username = username;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
