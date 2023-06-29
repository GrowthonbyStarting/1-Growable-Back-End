package com.growable.starting.dto.auth;

import com.growable.starting.model.Mentee;
import com.growable.starting.model.Mentor;
import com.growable.starting.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private User user;
    private String error;
    private String token;
    private Mentee mentee;
    private Mentor mentor;

    public AuthResponse(User user, String error, String token, Mentee mentee, Mentor mentor) {
        this.user = user;
        this.error = error;
        this.token = token;
        this.mentee = mentee;
        this.mentor = mentor;
    }

}
