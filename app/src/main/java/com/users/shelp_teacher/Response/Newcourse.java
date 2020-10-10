package com.users.shelp_teacher.Response;

public class Newcourse {
    private static String _id;

    public Newcourse(String _id) {
        this._id = _id;
    }

    public static String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

