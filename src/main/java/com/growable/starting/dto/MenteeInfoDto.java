package com.growable.starting.dto;

import com.growable.starting.model.type.Identity;
import lombok.Data;

@Data
public class MenteeInfoDto {

    private Long menteeId;

    private String name;

    private String email;

    private Identity identity;

    private int Point;

    private String StartingUrl;
}
