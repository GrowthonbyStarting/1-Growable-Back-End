package com.growable.starting.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.growable.starting.dto.auth.AuthRequest;
import com.growable.starting.dto.auth.AuthResponse;
import com.growable.starting.jwt.JwtProperties;
import com.growable.starting.model.User;
import com.growable.starting.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class AuthHelper {
    public static ResponseEntity<AuthResponse> handleRedirect(AuthRequest authRequest, UserRepository userRepository, String client_id) {
        String code = authRequest.getAuthCode();
        String redirectUri = authRequest.getRedirectURL();
        AuthResponse authResponse;

        if (code == null) {
            authResponse = new AuthResponse(null, "Authorization code is missing", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse);
        }

        try {
            String accessToken = AuthToken.getAccessToken(code, redirectUri, client_id);
            User authenticatedUser = AuthUser.getAuthenticatedUser(accessToken, userRepository);
            String JwtToken = createToken(authenticatedUser);
            if (authenticatedUser != null) {
                authResponse = new AuthResponse(authenticatedUser, null, JwtToken);
                return ResponseEntity.ok(authResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        authResponse = new AuthResponse(null, "Error occurred during authentication", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(authResponse);
    }

    public static String createToken(User user) {
        // Jwt 생성 후 헤더에 추가해서 보내줌

        return JWT.create()
                .withSubject(user.getKakaoEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getUserCode())
                .withClaim("nickname", user.getKakaoNickname())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
}
