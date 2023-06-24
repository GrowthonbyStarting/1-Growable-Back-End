package com.growable.starting.controller;

import com.growable.starting.dto.auth.AuthRequest;
import com.growable.starting.dto.auth.AuthResponse;
import com.growable.starting.dto.auth.KakaoProfile;
import com.growable.starting.dto.auth.OauthToken;
import com.growable.starting.model.Lecture;
import com.growable.starting.model.User;
import com.growable.starting.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${kakao.clientId}")
    String client_id;

    @GetMapping("/send-key")
    public ResponseEntity<Map<String, String>> sendKey(){
        Map<String, String> res = new HashMap<>();
        res.put("apikey","92834c027009e695e46bf5163f5a8643");
        System.out.println(client_id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/getCode")
    public ResponseEntity<AuthResponse> handleRedirect(@RequestBody AuthRequest authRequest) {
        String code = authRequest.getAuthCode();
        String redirectUri = authRequest.getRedirectURL();
        AuthResponse authResponse;

        if (code == null) {
            // 인가 코드가 없는 경우 에러 처리
            authResponse = new AuthResponse(null, "Authorization code is missing");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse);
        }

        try {
            String accessToken = getAccessToken(code, redirectUri);

            // 인증된 사용자 정보 가져오기
            User authenticatedUser = getAuthenticatedUser(accessToken);
            if (authenticatedUser != null) {
                authResponse = new AuthResponse(authenticatedUser, null);
                return ResponseEntity.ok(authResponse);
            }

        } catch (Exception e) {
            // 예외 처리
        }

        authResponse = new AuthResponse(null, "Error occurred during authentication");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(authResponse);
    }

    private User getAuthenticatedUser(String accessToken) {
        KakaoProfile kakaoProfile = getKakaoProfile(accessToken);
        return saveKakaoUserInfo(kakaoProfile);
    }

    private String getAccessToken(String code, String redirectUri) throws Exception {
        OauthToken oauthToken = getOauthToken(code, redirectUri);
        return oauthToken.getAccess_token();
    }


    private OauthToken getOauthToken(String code, String redirectUri) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        String clientId = "92834c027009e695e46bf5163f5a8643";
        String grantType = "authorization_code";

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("grant_type", grantType);
        paramMap.add("client_id", clientId);
        paramMap.add("redirect_uri", redirectUri);
        paramMap.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramMap, headers);
        ResponseEntity<OauthToken> responseEntity = restTemplate.postForEntity(tokenUrl, request, OauthToken.class);

        return responseEntity.getBody();
    }


    private boolean isUserAuthenticated(String accessToken) {
        KakaoProfile kakaoProfile = getKakaoProfile(accessToken);
        User user = saveKakaoUserInfo(kakaoProfile);
        return user != null;
    }


    private KakaoProfile getKakaoProfile(String accessToken) {
        String profileUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<KakaoProfile> responseEntity = restTemplate.exchange(profileUrl, HttpMethod.GET, requestEntity, KakaoProfile.class);

        return responseEntity.getBody();
    }

    private User saveKakaoUserInfo(KakaoProfile kakaoProfile) {
        KakaoProfile.KakaoAccount kakaoAccount = kakaoProfile.getKakao_account();

        User newUser = new User();
        newUser.setKakaoId(kakaoProfile.getId());
        newUser.setKakaoProfileImg(kakaoAccount.getProfile().getThumbnail_image_url());
        newUser.setKakaoNickname(kakaoAccount.getProfile().getNickname());
        newUser.setKakaoEmail(kakaoAccount.getEmail());
        newUser.setUserRole("user");

        return userRepository.save(newUser);
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String errorMessage) {
        return ResponseEntity.status(status).body(Collections.singletonMap("error", errorMessage));
    }
}
