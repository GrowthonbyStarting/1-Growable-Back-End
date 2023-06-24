package com.growable.starting.dto;

import com.growable.starting.model.Lecture;
import com.growable.starting.model.Mentor;
import com.growable.starting.model.type.LectureStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class LectureDto {

    private Long id;
    private String title;
    private LocalDate recruitmentStartDate;
    private LocalDate recruitmentEndDate;
    private int capacity;
    private double fee;
    private LocalDate lectureStartDate;
    private LocalDate lectureEndDate;
    private String mentorName;
    private String teamUrl;

    public Lecture toEntity(Mentor mentor) {
        Lecture lecture = Lecture.builder()
                .title(title)
                .recruitmentStartDate(recruitmentStartDate)
                .recruitmentEndDate(recruitmentEndDate)
                .capacity(capacity)
                .fee(fee)
                .lectureStartDate(lectureStartDate)
                .lectureEndDate(lectureEndDate)
                .mentorName(mentorName)
                .teamUrl(teamUrl)
                .mentor(mentor)
                .build();

        return lecture;
    }
}