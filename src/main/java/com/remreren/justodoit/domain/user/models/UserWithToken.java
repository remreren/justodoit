package com.remreren.justodoit.domain.user.models;

public record UserWithToken(
        User user,
        String accessToken
) {
}
