package com.remreren.justodoit.domain.todo;

import com.remreren.justodoit.domain.todo.models.Todo;
import com.remreren.justodoit.domain.todo.models.TodoEntity;
import com.remreren.justodoit.domain.todo.models.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TodoServiceTest {

    @Mock
    private TodoRepository repository;

    private TodoMapper mapper;
    private TodoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mapper = new TodoMapperImpl();
        service = new TodoService(repository, mapper);
    }

    @Test
    void patchTodo() {
        // Given
        String user = "testUser";
        LocalDateTime createdAt = LocalDateTime.now();
        Todo newTodo = new Todo("id1", "new title", "new description", null, TodoStatus.IN_PROGRESS, createdAt, createdAt);

        TodoEntity oldEntity = new TodoEntity("id1", "old title", "old description", null, TodoStatus.IN_PROGRESS, createdAt, createdAt, user, user, 1L);
        TodoEntity newEntity = new TodoEntity("id1", "new title", "new description", null, TodoStatus.IN_PROGRESS, createdAt, createdAt, user, user, 2L);

        when(repository.findByIdAndCreatedBy(newTodo.id(), user)).thenReturn(Optional.of(oldEntity));
        when(repository.save(oldEntity)).thenReturn(newEntity);

        // When
        Todo result = service.patchTodo(newTodo, user);

        // Then
        assertEquals(newTodo, result);
    }
}