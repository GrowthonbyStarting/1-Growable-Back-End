package com.growable.starting.model;

import com.growable.starting.model.type.Identity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mentee")
public class Mentee {

    @Id
    @Column(name = "mentee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menteeId;

    @Column
    private String imageUrl;

    @Column
    private String name;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column
    private Identity identity;

    @Column
    private int Point;

    @Column
    private String phoneNumber;

    @Column
    private String StartingUrl;

    @OneToOne
    @JoinColumn(name = "user_code")
    private User user;

    @Column
    private String profileImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @Column
    private String bankName;

    @Column
    private String account;

    @Builder
    public Mentee(String  imageUrl, String name, String email,
                Identity identity,int point,String phoneNumber, String StartingUrl, User user,String bankName, String account) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.email = email;
        this.identity = identity;
        this.Point = point;
        this.phoneNumber = phoneNumber;
        this.StartingUrl = StartingUrl;
        this.user = user;
        this.bankName = bankName;
        this.account = account;
    }
}
