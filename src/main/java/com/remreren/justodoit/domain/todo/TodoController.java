package com.remreren.justodoit.domain.todo;

import com.remreren.justodoit.domain.todo.models.CreateTodo;
import com.remreren.justodoit.domain.todo.models.Todo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    private final TodoService service;
    private final TodoMapper mapper;

    @GetMapping
    public ResponseEntity<List<Todo>> listTodos(@AuthenticationPrincipal UserDetails principal) {
        List<Todo> todoList = service.listTodos(principal.getUsername());
        return ResponseEntity.ok(todoList);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody @Validated CreateTodo request) {
        Todo created = service.createTodo(mapper.toModel(request));
        return ResponseEntity.ok(created);
    }
}
