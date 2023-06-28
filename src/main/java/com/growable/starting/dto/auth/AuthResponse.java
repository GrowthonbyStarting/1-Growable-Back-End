package com.growable.starting.dto.auth;

import com.growable.starting.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private User user;
    private String error;
    private String token;

    public AuthResponse(User user, String error, String token) {
        this.user = user;
        this.error = error;
        this.token = token;
    }

}
