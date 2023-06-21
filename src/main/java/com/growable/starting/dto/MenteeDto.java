package com.growable.starting.dto;

import com.growable.starting.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MenteeDto implements Serializable {

    private Long menteeId;
    private String imageUrl;
    private String name;
    private String email;
    private Identity identity;
    private int point;
    private String phoneNumber;
    private String startingUrl;
    private String profileImageUrl;
    private Long userId;
    private Long lectureId;
}
