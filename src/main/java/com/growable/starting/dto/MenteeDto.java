package com.growable.starting.dto;

import com.growable.starting.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenteeDto {

    private Long menteeId;
    private String imageUrl;
    private String name;
    private String email;
    private Identity identity;
    private int point;
    private String phoneNumber;
    private String startingUrl;
    private User user;
    private String profileImageUrl;

}
