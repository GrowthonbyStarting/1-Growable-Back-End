package com.growable.starting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lecture_experience")
public class LectureExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String lectureTitle;
    @Column
    private String companyName;
    @Column
    private String lectureType;
    @Column
    private String lectureField;
    @Column
    private String startDate;
    @Column
    private String endDate; // 허용되는 null 값입니다.

}
