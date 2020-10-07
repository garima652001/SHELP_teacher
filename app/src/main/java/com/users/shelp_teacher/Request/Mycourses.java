package com.users.shelp_teacher.Request;

public class Mycourses {
    String userId;

    public Mycourses(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
