package com.growable.starting.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private String content;
    private Long lectureId;
    private String lectureTitle;
    private Long menteeId;
    private String menteeName;

}
