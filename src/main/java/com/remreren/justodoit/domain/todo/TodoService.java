package com.remreren.justodoit.domain.todo;

import com.remreren.justodoit.domain.todo.models.Todo;
import com.remreren.justodoit.domain.todo.models.TodoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;
    private final TodoMapper mapper;

    @Transactional(readOnly = true)
    public List<Todo> listTodos(String user) {
        return repository.findAllByCreatedBy(user)
                         .map(mapper::toModel)
                         .collect(Collectors.toList());
    }

    @Transactional
    public Todo createTodo(Todo todo) {
        TodoEntity ent = mapper.toEntity(todo);
        ent = repository.save(ent);

        return mapper.toModel(ent);
    }

    @Transactional
    public Todo updateTodo(Todo newTodo) {
        TodoEntity ent = repository.findById(newTodo.id())
                                   .map(oldTodo -> updateTodoEntity(oldTodo, newTodo))
                                   .orElseThrow(() -> new IllegalArgumentException("Todo not found"));

        repository.save(ent);

        return mapper.toModel(ent);
    }

    @Transactional
    public Todo patchTodo(Todo newTodo) {
        TodoEntity ent = repository.findById(newTodo.id())
                                   .map(oldTodo -> patchTodoEntity(oldTodo, newTodo))
                                   .orElseThrow(() -> new IllegalArgumentException("Todo not found"));

        repository.save(ent);

        return mapper.toModel(ent);
    }

    // TODO: update this conversion methods to be part of the mapper
    private TodoEntity updateTodoEntity(TodoEntity oldTodo, Todo newTodo) {
        oldTodo.setTitle(newTodo.title());
        oldTodo.setDescription(newTodo.description());
        oldTodo.setStatus(newTodo.status());
        oldTodo.setDueDate(newTodo.dueDate());
        return oldTodo;
    }

    private TodoEntity patchTodoEntity(TodoEntity oldTodo, Todo newTodo) {
        if (newTodo.title() != null) {
            oldTodo.setTitle(newTodo.title());
        }

        if (newTodo.description() != null) {
            oldTodo.setDescription(newTodo.description());
        }

        if (newTodo.status() != null) {
            oldTodo.setStatus(newTodo.status());
        }

        if (newTodo.dueDate() != null) {
            oldTodo.setDueDate(newTodo.dueDate());
        }

        return oldTodo;
    }
}
