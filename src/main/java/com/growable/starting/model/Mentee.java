package com.growable.starting.model;

import com.growable.starting.dto.Identity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mentee")
public class Mentee {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menteeId;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private Identity identity;

    @Column
    private int Point;

    @Column
    private String StartingUrl;

    @OneToOne
    @JoinColumn(name = "user_code")
    private User user;

}
