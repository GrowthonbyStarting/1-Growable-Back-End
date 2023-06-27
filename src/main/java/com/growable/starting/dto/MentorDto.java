package com.growable.starting.dto;

import com.growable.starting.model.Company;
import com.growable.starting.model.LectureExperience;
import com.growable.starting.model.User;
import com.growable.starting.model.type.Identity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
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
    private String profileImageUrl;
    private User user;
    private List<Company> company;
    private List<LectureExperience> lectureExperiences;
    private int like;

}
