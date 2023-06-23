package com.growable.starting.controller;

import com.growable.starting.dto.auth.KakaoProfile;
import com.growable.starting.dto.auth.OauthToken;
import com.growable.starting.model.User;
import com.growable.starting.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ApiOperation("카카오 로그인 시작") //1번
    @GetMapping("/kakao-login")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String clientId = "92834c027009e695e46bf5163f5a8643";
        String redirectUri = "http://13.209.18.185:8080/api/redirect-uri";
        String responseType = "code";

        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=" + responseType;

        response.sendRedirect(kakaoAuthUrl);
    }

    @ApiOperation("토큰 발급 및 유저 정보 저장") //2번
    @GetMapping("/redirect-uri")
    public String handleRedirect(@RequestParam(required = false) String code) {

        String redirectUri = "http://13.209.18.185:8080/api/redirect-uri";

        if (code == null) {
            // 인가 코드가 없는 경우 에러 처리
            return String.valueOf(buildErrorResponse(HttpStatus.BAD_REQUEST, "Authorization code is missing"));
        }

        try {
            String accessToken = getAccessToken(code, redirectUri);

            if (isUserAuthenticated(accessToken)) {
                sendTokenByReact(accessToken);
                return "redirect:/"; // 메인 페이지로 리다이렉트
            }

        } catch (Exception e) {
            // 예외 처리
        }
        return String.valueOf(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred during authentication"));
    }

    //3번
    //카카오에서 받은 인가코드로 토큰을 발급 받는다!
    // getAccessToken 메소드가 받아오 토큰에서 accessToken 리턴
    private String getAccessToken(String code, String redirectUri) throws Exception {
        OauthToken oauthToken = getOauthToken(code, redirectUri);
        return oauthToken.getAccess_token();
    }

    //4번
    // getAccessToken 메소드에서 넘어와서 카카오서버에 토큰을 달라고 요청
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

    //5번
    //토큰발급받은 사용자 이시네요! 축하드립니다. 회원가입 해드릴께요.
    private boolean isUserAuthenticated(String accessToken) {
        KakaoProfile kakaoProfile = getKakaoProfile(accessToken);
        User user = saveKakaoUserInfo(kakaoProfile);
        return user != null;
    }


    //6번
    // 발급 받으신 토큰으로 사용자님 정보를 카카오에서 받아오겠습니다.
    private KakaoProfile getKakaoProfile(String accessToken) {
        String profileUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<KakaoProfile> responseEntity = restTemplate.exchange(profileUrl, HttpMethod.GET, requestEntity, KakaoProfile.class);

        return responseEntity.getBody();
    }

    //7번
    //자 정보도 받아오셨고 일단 기본정보만 사용해서 저장할께요(이메일, 프로필사진, 닉네임)
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

    //8번
    //사용자님의 accessToken React의 로컬스토리지에 저장해야되서 보낼께여!
    @GetMapping("/getToken")
    public ResponseEntity<?> sendTokenByReact(String accessToken){
        Map<String, String> resBody = new HashMap<>();
        resBody.put("accessToken", accessToken);
        return ResponseEntity.ok(resBody);
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String errorMessage) {
        return ResponseEntity.status(status).body(Collections.singletonMap("error", errorMessage));
    }

    /*
1. `@ApiOperation("카카오 로그인 시작")`:
카카오 로그인 프로세스를 시작하기 위해 사용자를 카카오 로그인 페이지로 리다이렉트하며 앱의 클라이언트 ID 및 리다이렉트 URI를 전달합니다.

2. `@ApiOperation("토큰 발급 및 유저 정보 저장")`:
카카오 서버로부터 인가 코드를 받고 이를 사용하여 액세스 토큰을 발급받습니다. 사용자가 이미 인증된 경우에는 리액트 로컬스토리지에 액세스 토큰을 보냅니다.

3. `private String getAccessToken(String code, String redirectUri)`: 인가 코드를 사용하여 토큰을 발급받는 메서드입니다. 발급받은 토큰 중에서 액세스 토큰을 반환합니다.

4. `private OauthToken getOauthToken(String code, String redirectUri)`:
인가 코드와 함께 카카오 서버에 접근 토큰을 요청하는 메서드입니다.

5. `private boolean isUserAuthenticated(String accessToken)`: 사용자가 인증되어 있는지 확인하는 메서드입니다. 인증된 사용자라면, 사용자 정보를 저장한 뒤 액세스 토큰을 리액트가 사용 가능한 방식으로 전달 합니다.

6. `private KakaoProfile getKakaoProfile(String accessToken)`:
액세스 토큰을 사용하여 카카오 서버에서 사용자 프로파일 정보를 가져오는 메서드입니다.

7. `private User saveKakaoUserInfo(KakaoProfile kakaoProfile)`:
카카오 프로파일 정보를 토대로 사용자 정보를 데이터베이스에 저장하는 메서드입니다. 이 메서드에서는 기본 정보(이메일, 프로필 이미지, 닉네임)만 저장합니다.

8. `@GetMapping("/getToken")`:
액세스 토큰을 리액트 로컬스토리지에 저장할 수 있도록 사용자에게 송신하는 메서드입니다.

9. `private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String errorMessage)`:
인증 과정에서 예외 상황 발생 시 오류 메시지와 함께 HTTP 응답을 구성하는 메서드입니다.
     */
}
