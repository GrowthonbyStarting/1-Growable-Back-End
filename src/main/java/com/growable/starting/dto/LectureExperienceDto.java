package com.growable.starting.dto;

import com.growable.starting.model.LectureExperience;
import lombok.Data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LectureExperienceDto {

    private Long id;
    private String lectureTitle;
    private String companyName;
    private String lectureType;
    private String lectureField;
    private String startDate;
    private String endDate;

    public LectureExperience toEntity() {
        return LectureExperience.builder()
                .lectureTitle(lectureTitle)
                .companyName(companyName)
                .lectureType(lectureType)
                .lectureField(lectureField)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}