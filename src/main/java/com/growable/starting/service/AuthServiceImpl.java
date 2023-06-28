package com.growable.starting.service;

import com.growable.starting.dto.auth.AuthRequest;
import com.growable.starting.dto.auth.AuthResponse;
import com.growable.starting.repository.UserRepository;
import com.growable.starting.util.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{

    @Value("${kakao.clientId}")
    String client_id;

    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, String> getApiKey() {
        Map<String, String> res = new HashMap<>();
        res.put("apikey", client_id);
        return res;
    }

    @Override
    public ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest) {
        return AuthHelper.handleRedirect(authRequest, userRepository, client_id);
    }
}
