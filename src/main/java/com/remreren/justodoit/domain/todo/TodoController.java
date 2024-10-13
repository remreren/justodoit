package com.remreren.justodoit.domain.todo;

import com.remreren.justodoit.contract.TodoControllerContract;
import com.remreren.justodoit.domain.todo.models.CreateTodo;
import com.remreren.justodoit.domain.todo.models.Todo;
import com.remreren.justodoit.domain.todo.models.TodoList;
import com.remreren.justodoit.domain.todo.models.UpdateTodo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todo")
public class TodoController implements TodoControllerContract {

    private final TodoService service;
    private final TodoMapper mapper;

    @GetMapping
    public ResponseEntity<TodoList> listTodos(@AuthenticationPrincipal UserDetails principal) {
        List<Todo> todoList = service.listTodos(principal.getUsername());
        return ResponseEntity.ok(new TodoList(todoList));
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody @Validated CreateTodo request) {
        Todo created = service.createTodo(mapper.toModel(request));
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id,
                                           @RequestBody @Validated UpdateTodo todo,
                                           @AuthenticationPrincipal UserDetails principal) {
        Todo updated = service.updateTodo(mapper.toModel(todo, id), principal.getUsername());
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/")
    public ResponseEntity<Todo> patchTodo(@PathVariable String id,
                                          @RequestBody @Validated UpdateTodo todo,
                                          @AuthenticationPrincipal UserDetails principal) {
        Todo patched = service.patchTodo(mapper.toModel(todo, id), principal.getUsername());
        return ResponseEntity.ok(patched);
    }
}
