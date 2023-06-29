package com.growable.starting.dto;

import com.growable.starting.model.type.Identity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenteeDto {

    private Long menteeId;
    private String imageUrl;
    private String name;
    private String email;
    private Identity identity;
    private int point;
    private String phoneNumber;
    private String StartingUrl;
    private String profileImageUrl;
    private String bankName;
    private String account;

}
