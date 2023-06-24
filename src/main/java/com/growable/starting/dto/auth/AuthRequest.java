package com.growable.starting.dto.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class AuthRequest {

    private String authCode;
    private String redirectURL;
}
