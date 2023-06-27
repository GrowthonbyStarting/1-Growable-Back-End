package com.growable.starting.dto.auth;

import com.growable.starting.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private User user;
    private String error;

    public AuthResponse(User user, String error) {
        this.user = user;
        this.error = error;
    }

}
