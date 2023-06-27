package com.growable.starting.dto;

import com.growable.starting.model.type.Identity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class MenteeDto implements Serializable {

    private Long menteeId;
    private String imageUrl;
    private String name;
    private String email;
    private Identity identity;
    private int point;
    private String phoneNumber;
    private String StartingUrl;
    private String profileImageUrl;
}
