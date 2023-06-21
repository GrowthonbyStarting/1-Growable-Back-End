package com.growable.starting.dto;

import com.growable.starting.model.type.Identity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MentorDto implements Serializable {

    private Long mentorId;
    private String name;
    private String email;
    private Identity identity;
    private int point;
    private String chatUrl;
    private String category;
    private String subcategory;
    private List<String> keywords;
    private Long userId;
    private List<CompanyDto> companyInfos;
    private List<LectureExperienceDto> lectureExperiences;
    private String profileImageUrl;
    private List<LectureDto> lectures;

}
