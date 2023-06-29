package com.growable.starting.controller;

import com.growable.starting.dto.auth.AuthRequest;
import com.growable.starting.dto.auth.AuthResponse;
import com.growable.starting.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/api")
public class AuthController {

    private final AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @GetMapping("/send-key")
    public ResponseEntity<Map<String, String>> sendKey() {
        return ResponseEntity.ok(authService.getApiKey());
    }

    @PostMapping("/getCode")
    public ResponseEntity<AuthResponse> handleRedirect(@RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }
}
