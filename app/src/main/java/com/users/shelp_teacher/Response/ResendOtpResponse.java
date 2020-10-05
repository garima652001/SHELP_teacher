package com.users.shelp_teacher.Response;

public class ResendOtpResponse {
    String message,token;

    public ResendOtpResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
