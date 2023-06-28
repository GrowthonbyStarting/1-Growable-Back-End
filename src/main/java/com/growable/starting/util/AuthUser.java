package com.growable.starting.util;

import com.growable.starting.dto.auth.KakaoProfile;
import com.growable.starting.model.User;
import com.growable.starting.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class AuthUser {

    public static User getAuthenticatedUser(String accessToken, UserRepository userRepository) {
        KakaoProfile kakaoProfile = getKakaoProfile(accessToken);
        return saveKakaoUserInfo(kakaoProfile, userRepository);
    }

    private static KakaoProfile getKakaoProfile(String accessToken) {
        String profileUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<KakaoProfile> responseEntity = restTemplate.exchange(profileUrl, HttpMethod.GET, requestEntity, KakaoProfile.class);

        return responseEntity.getBody();
    }

    private static User saveKakaoUserInfo(KakaoProfile kakaoProfile, UserRepository userRepository) {

        KakaoProfile.KakaoAccount kakaoAccount = kakaoProfile.getKakao_account();
        Long kakaoId = kakaoProfile.getId();

        User newUser = new User();
        newUser.setKakaoId(kakaoId);
        newUser.setKakaoProfileImg(kakaoAccount.getProfile().getThumbnail_image_url());
        newUser.setKakaoNickname(kakaoAccount.getProfile().getNickname());
        newUser.setKakaoEmail(kakaoAccount.getEmail());
        newUser.setUserRole("USER");

        return userRepository.save(newUser);
    }
}