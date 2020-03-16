package com.example.kmsquiz.data.DB;

public class IsAdmin {

    private int UserId;
    private boolean Admin;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public boolean isAdmin() {
        return Admin;
    }

    public void setAdmin(boolean admin) {
        Admin = admin;
    }
}

