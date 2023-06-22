package com.growable.starting.controller;

import com.growable.starting.model.User;
import com.growable.starting.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 프론트에서 인가코드 돌려 받는 주소
    // 인가 코드로 엑세스 토큰 발급 -> 사용자 정보 조회 -> DB 저장 -> jwt 토큰 발급 -> 프론트에 토큰 전달
    @GetMapping("/auth/token")
    public ResponseEntity<String> handleAuthCode(@RequestBody Map<String, String> requestBody) {
        String code = requestBody.get("code");
        String redirect_uri = requestBody.get("redirect_uri");

        // 인가 코드를 처리하고 JWT 토큰을 반환합니다.
        String jwtToken = authService.processAuthCode(code,redirect_uri);

        // JWT 토큰을 프론트엔드로 반환합니다.
        return ResponseEntity.ok().body(jwtToken);
    }

    // jwt 토큰으로 유저정보 요청하기
    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {

        User user = authService.getUser(request);

        return ResponseEntity.ok().body(user);
    }
}
