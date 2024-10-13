package com.remreren.justodoit.domain.todo.models;

import java.time.LocalDateTime;

public record Todo(
        String id,
        String title,
        String description,
        LocalDateTime dueDate,
        TodoStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
