package com.growable.starting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LectureStatus {

    NOT_STARTED("ROLE_NOT_STARTED"), //시작전
    RECRUITING("ROLE_RECRUITING"), //모집중
    RECRUITMENT_ENDED("ROLE_RECRUITMENT_ENDED"), //모집종료
    LECTURE_ENDED("ROLE_LECTURE_ENDED"); //강의 종료

    private final String value;
}
