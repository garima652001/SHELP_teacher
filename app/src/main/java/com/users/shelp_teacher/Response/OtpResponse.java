package com.users.shelp_teacher.Response;

public class OtpResponse {
    String message,token,userId, username;

    public OtpResponse(String message, String token, String userId, String username) {
        this.message = message;
        this.token = token;
        this.userId = userId;
        this.username = username;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
