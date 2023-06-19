package com.growable.starting.dto;

import lombok.Data;

@Data
public class MentorInfoDto {

    private Long mentorId;

    private String name;

    private String email;

    private Identity identity;

    private int Point;

    private int tuition;

    private String chatUrl;

}