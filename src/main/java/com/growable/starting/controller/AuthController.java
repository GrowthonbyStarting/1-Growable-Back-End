package com.growable.starting.controller;

import com.growable.starting.dto.auth.KakaoProfile;
import com.growable.starting.dto.auth.OauthToken;
import com.growable.starting.model.User;
import com.growable.starting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

//api/auth/token
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/getRedirectUri")
    public @ResponseBody Map<String, String> getRedirectUri() {
        Map<String, String> response = new HashMap<>();
        response.put("redirectUri", "http://13.209.18.185/api/redirect-uri");
        return response;
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

    @GetMapping("/redirect-uri")
    public ResponseEntity<String> handleRedirect(@RequestParam(required = false) String code) {
        String redirectUri = "http://13.209.18.185:8080/api/redirect-uri";

        if (code == null) {
            // 인가 코드가 없는 경우 에러 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization code is missing");
        }

        try {
            OauthToken oauthToken = getOauthToken(code, redirectUri);
            String accessToken = oauthToken.getAccess_token();

            KakaoProfile kakaoProfile = getKakaoProfile(accessToken);

            User user = saveKakaoUserInfo(kakaoProfile);

            if (user != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create("http://13.209.18.185:3000/about"));
                // 로그인 성공
                return ResponseEntity.status(HttpStatus.OK).body("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during authentication");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during authentication");
        }
    }

    @GetMapping("/kakao-login")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String clientId = "92834c027009e695e46bf5163f5a8643";
        String redirectUri = "http://13.209.18.185/api/redirect-uri";
        String responseType = "code";

        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=" + responseType;

        response.sendRedirect(kakaoAuthUrl);
    }
}
