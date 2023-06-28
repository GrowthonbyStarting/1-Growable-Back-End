package com.growable.starting.util;

import com.growable.starting.dto.auth.OauthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AuthToken {
    public static String getAccessToken(String code, String redirectUri, String apiKey) throws Exception {
        OauthToken oauthToken = getOauthToken(code, redirectUri, apiKey);
        return oauthToken.getAccess_token();
    }

    private static OauthToken getOauthToken(String code, String redirectUri, String apiKey) {
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
}
