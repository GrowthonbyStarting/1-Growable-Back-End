package com.growable.starting.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OAuthTokenDto {

    private String accessToken;

    private String tokenType;

    private String refreshToken;

    private int expiresIn;

    private String scope;

    private String refreshTokenExpiresIn;

}
