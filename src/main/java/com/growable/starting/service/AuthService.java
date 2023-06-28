package com.growable.starting.service;

import com.growable.starting.dto.auth.AuthRequest;
import com.growable.starting.dto.auth.AuthResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {

    Map<String, String> getApiKey();

    ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest);
}
