package com.users.shelp_teacher.Request;

public class Resetotp {
    String email;

    public Resetotp(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
