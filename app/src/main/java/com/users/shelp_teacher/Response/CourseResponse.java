package com.users.shelp_teacher.Response;

public class CourseResponse {
    String message;
            private Newcourse newCourse;

    public CourseResponse(String message, Newcourse newCourse) {
        this.message = message;
        this.newCourse = newCourse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   /* public String getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(String newCourse) {
        this.newCourse = newCourse;
    }*/
}
