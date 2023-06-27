package com.growable.starting.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    private String authCode;
    private String redirectURL;
}
