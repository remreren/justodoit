package com.remreren.justodoit.domain.todo.models;

import java.util.List;

public record TodoList(
        List<Todo> items
) {
}
