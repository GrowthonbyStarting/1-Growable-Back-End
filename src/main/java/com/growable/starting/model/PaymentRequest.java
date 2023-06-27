package com.growable.starting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String imp_uid;
    private String merchant_uid;
    private int amount;
    private String buyerEmail;
}