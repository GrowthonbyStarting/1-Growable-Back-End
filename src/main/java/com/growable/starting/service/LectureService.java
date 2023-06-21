package com.growable.starting.service;

import com.growable.starting.dto.LectureDto;
import com.growable.starting.model.Lecture;

public interface LectureService {

    Lecture createLecture(Long mentorId, LectureDto lectureDto);
}
