package com.remreren.justodoit.domain.user.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.time.LocalDateTime;

@Getter
@Setter
@Document
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(delimiter = "::", strategy = GenerationStrategy.UNIQUE)
    private String id;

    @Field
    @NotEmpty(message = "Password is required")
    private String password;

    @Field
    @NotEmpty(message = "Email is required")
    private String email;

    @Field
    @CreatedDate
    private LocalDateTime createdAt;

    @Field
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    private long version;
}
