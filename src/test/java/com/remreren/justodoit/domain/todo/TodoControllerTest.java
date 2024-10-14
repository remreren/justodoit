package com.remreren.justodoit.domain.todo;

import com.remreren.justodoit.domain.todo.models.CreateTodo;
import com.remreren.justodoit.domain.todo.models.Todo;
import com.remreren.justodoit.domain.todo.models.UpdateTodo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TodoControllerTest {

    @Mock
    private TodoService service;

    private TodoMapper mapper;

    private TodoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper = new TodoMapperImpl();
        controller = new TodoController(service, mapper);
    }

    @Test
    void listTodos() {
        // Given
        Todo todo1 = new Todo("id1", "title1", "description1", null, null, null, null);
        Todo todo2 = new Todo("id2", "title2", "description2", null, null, null, null);

        when(service.listTodos("example@example.com")).thenReturn(List.of(todo1));
        when(service.listTodos("example.other@example.com")).thenReturn(List.of(todo2));

        UserDetails principal1 = new User("example@example.com", "password", List.of());
        UserDetails principal2 = new User("example.other@example.com", "password", List.of());

        // When
        List<Todo> result1 = Objects.requireNonNull(controller.listTodos(principal1).getBody()).items();
        List<Todo> result2 = Objects.requireNonNull(controller.listTodos(principal2).getBody()).items();

        // Then
        assertEquals(1, result1.size());
        assertEquals(1, result2.size());

        assertEquals("id1", result1.getFirst().id());
        assertEquals("id2", result2.getFirst().id());
    }

    @Test
    void createTodo() {
        // Given
        Todo todo = new Todo("id", "title", "description", null, null, null, null);
        when(service.createTodo(any())).thenReturn(todo);

        CreateTodo request = new CreateTodo("title", "description", null);

        // When
        Todo result = controller.createTodo(request).getBody();

        // Then
        assertNotNull(result);
        assertEquals(todo, result);

        verify(service, times(1)).createTodo(mapper.toModel(request));
    }

    @Test
    void updateTodo() {
        // Given
        Todo todo = new Todo("id", "title", "description", null, null, null, null);
        when(service.updateTodo(any(), any())).thenReturn(todo);

        UpdateTodo request = new UpdateTodo("title", "description", null, null);
        UserDetails principal = new User("example.example.com", "password", List.of());

        // When
        Todo result = controller.updateTodo("id", request, principal).getBody();

        // Then
        assertNotNull(result);
        assertEquals(todo, result);

        verify(service, times(1)).updateTodo(mapper.toModel(request, "id"), "example.example.com");
    }
}