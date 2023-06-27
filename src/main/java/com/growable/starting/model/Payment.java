package com.growable.starting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {

    @Id
    private Long paymentId;

    private String imp_uid;

    private String merchant_uid;

    private int amount;

    private String buyerEmail;
}
