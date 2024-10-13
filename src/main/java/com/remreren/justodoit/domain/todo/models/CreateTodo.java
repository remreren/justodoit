package com.remreren.justodoit.domain.todo.models;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateTodo(
        @Size(min = 2, max = 100, message = "Title length must be between 2 and 100") String title,
        @Size(max = 1000, message = "Description can be maximum 1000 characters") String description,
        LocalDateTime dueDate
) {
}
