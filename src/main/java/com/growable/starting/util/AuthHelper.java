package com.growable.starting.util;

import com.growable.starting.dto.auth.AuthRequest;
import com.growable.starting.dto.auth.AuthResponse;
import com.growable.starting.model.User;
import com.growable.starting.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthHelper {
    public static ResponseEntity<AuthResponse> handleRedirect(AuthRequest authRequest, UserRepository userRepository, String apiKey) {
        String code = authRequest.getAuthCode();
        String redirectUri = authRequest.getRedirectURL();
        AuthResponse authResponse;

        if (code == null) {
            authResponse = new AuthResponse(null, "Authorization code is missing");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse);
        }

        try {
            String accessToken = AuthToken.getAccessToken(code, redirectUri, apiKey);
            User authenticatedUser = AuthUser.getAuthenticatedUser(accessToken, userRepository);

            if (authenticatedUser != null) {
                authResponse = new AuthResponse(authenticatedUser, null);
                return ResponseEntity.ok(authResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        authResponse = new AuthResponse(null, "Error occurred during authentication");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(authResponse);
    }
}
