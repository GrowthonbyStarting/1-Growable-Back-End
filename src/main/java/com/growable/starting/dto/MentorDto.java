package com.growable.starting.dto;

import lombok.Data;

import java.util.List;

@Data
public class MentorDto {

    private Long mentorId;
    private String name;
    private String email;
    private Identity identity;
    private int point;
    private String chatUrl;
    private Long userId;
    private String category;
    private String subcategory;
    private List<String> keywords;
    private List<CompanyDto> companyInfos;
    private List<LectureExperienceDto> lectureExperiences;
}
