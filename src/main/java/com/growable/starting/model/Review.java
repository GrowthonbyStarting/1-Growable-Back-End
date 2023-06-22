package com.growable.starting.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "mentee_id", nullable = false)
    private Mentee mentee;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean like = false;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;



    public Review(Lecture lecture, Mentee mentee, String content) {
        this.lecture = lecture;
        this.mentee = mentee;
        this.content = content;
        this.like = false;
    }

}
