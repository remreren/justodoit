package com.remreren.justodoit.domain.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record User(
        String id,
        String email,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password
) {
}
