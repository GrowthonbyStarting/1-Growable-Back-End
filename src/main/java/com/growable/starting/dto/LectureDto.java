package com.growable.starting.dto;

import com.growable.starting.model.type.LectureStatus;
import lombok.Data;

@Data
public class LectureDto {

    private String title;
    private String recruitmentStartDate;
    private String recruitmentEndDate;
    private int capacity;
    private double fee;
    private String lectureStartDate;
    private String lectureEndDate;
    private LectureStatus status;
    private String mentorName;

}
