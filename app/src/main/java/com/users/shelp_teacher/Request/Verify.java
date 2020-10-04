package com.users.shelp_teacher.Request;

public class Verify {
    String token, Otp;

    public Verify(String token, String otp) {
        this.token = token;
        Otp = otp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }
}
