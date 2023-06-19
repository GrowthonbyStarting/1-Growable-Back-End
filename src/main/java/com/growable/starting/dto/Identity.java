package com.growable.starting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Identity {

    MENTEE("ROLE_MENTEE"),
    MENTOR("ROLE_MENTOR");

    private final String value;
}
