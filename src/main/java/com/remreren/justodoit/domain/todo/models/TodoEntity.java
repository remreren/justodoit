package com.remreren.justodoit.domain.todo.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
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
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TodoEntity {

    @Id
    @GeneratedValue(delimiter = "::", strategy = GenerationStrategy.UNIQUE)
    private String id;

    @Field
    @NotEmpty(message = "Title is required")
    private String title;

    @Field
    private String description = "";

    @Field
    private LocalDateTime dueDate = null;

    @Field
    private TodoStatus status = TodoStatus.IN_PROGRESS;

    @Field
    @CreatedDate
    private LocalDateTime createdAt;

    @Field
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Field
    @CreatedBy
    private String createdBy;

    @Field
    @LastModifiedBy
    private String updatedBy;

    @Version
    private Long version;
}
