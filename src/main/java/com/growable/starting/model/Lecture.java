package com.growable.starting.model;

import com.growable.starting.model.type.LectureStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title; //강의명
    @Column
    private LocalDate recruitmentStartDate; //모집시작
    @Column
    private LocalDate recruitmentEndDate; //모집종료
    @Column
    private int capacity; // 모집정원
    @Column
    private double fee; // 강의료
    @Column
    private LocalDate lectureStartDate;
    @Column
    private LocalDate lectureEndDate;
    @Column
    private String mentorName;

    @Column
    @Enumerated(EnumType.STRING)
    private LectureStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mentee> mentees;

    @Column
    private String teamUrl;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;

}