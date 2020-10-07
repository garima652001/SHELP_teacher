package com.users.shelp_teacher.Request;

public class Checkotp {
    String token,otp;

    public Checkotp(String token, String otp) {
        this.token = token;
        this.otp = otp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
