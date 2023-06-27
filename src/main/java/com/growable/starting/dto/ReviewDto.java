package com.growable.starting.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDto {
    private Long id;
    private Long lectureId;
    private Long menteeId;
    private String content;
    private double starScore;
    private Long mentorId;
}
