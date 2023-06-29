package com.growable.starting.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Identity {

    USER("ROLE_USER"),
    MENTEE("ROLE_MENTEE"),
    MENTOR("ROLE_MENTOR");

    private final String value;
}
