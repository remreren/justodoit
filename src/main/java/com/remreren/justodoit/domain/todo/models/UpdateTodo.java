package com.remreren.justodoit.domain.todo.models;

import java.time.LocalDateTime;

public record UpdateTodo(
        String title,
        String description,
        LocalDateTime dueDate,
        TodoStatus status
) {
}
