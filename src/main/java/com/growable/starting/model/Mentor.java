package com.growable.starting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.growable.starting.model.type.Identity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_id")
    private Long mentorId;

    @Column
    private String name;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column
    private Identity identity;

    @Column
    private int point;

    @Column
    private String chatUrl;

    @Column
    private String category;

    @Column
    private String subcategory;

    @Column
    private double starScore;

    @ElementCollection
    @CollectionTable(name = "mentor_keywords", joinColumns = @JoinColumn(name = "mentor_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mentor_id")
    private List<Company> companyInfos;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mentor_id")
    private List<LectureExperience> lectureExperiences;

    @Column
    private String profileImageUrl;

    @OneToOne
    @JoinColumn(name = "user_code")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Lecture> lectures;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;


}
