package com.growable.starting.controller;

import com.growable.starting.dto.OauthToken;
import com.growable.starting.jwt.JwtProperties;
import com.growable.starting.model.User;
import com.growable.starting.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @GetMapping("/auth")
    public ResponseEntity<?> getLogin(@RequestParam("code") String code) {

        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = authService.getAccessToken(code);

        if (oauthToken == null) {
            throw new RuntimeException("Failed to obtain access token");
        }
        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        String jwtToken = authService.SaveUserAndGetToken(oauthToken.getAccess_token());

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        return ResponseEntity.ok().headers(headers).body("success");
    }

    // jwt 토큰으로 유저정보 요청하기
    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {

        User user = authService.getUser(request);

        return ResponseEntity.ok().body(user);
    }


}
