package com.growable.starting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EnrollmentDto {
    private Long id;
    private Long lectureId;
    private Long menteeId;
    private Long mentorId;

}
