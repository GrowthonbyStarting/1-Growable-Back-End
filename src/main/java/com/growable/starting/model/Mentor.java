package com.growable.starting.model;

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

    @Column
    private Identity identity;

    @Column
    private int point;

    @Column
    private String chatUrl;

    @OneToOne
    @JoinColumn(name = "user_code")
    private User user;

    @Column
    private String category;

    @Column
    private String subcategory;

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

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecture> lectures;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;


}
