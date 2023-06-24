package com.growable.starting.dto.auth;

import com.growable.starting.model.User;

public class AuthResponse {
    private User user;
    private String error;

    public AuthResponse(User user, String error) {
        this.user = user;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public String getError() {
        return error;
    }
}
