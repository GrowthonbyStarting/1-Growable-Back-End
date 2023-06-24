package com.growable.starting.dto;

import com.growable.starting.model.type.LectureStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureWithStatusDto {

    private Long id;

    private LectureStatus lectureStatus;
}
