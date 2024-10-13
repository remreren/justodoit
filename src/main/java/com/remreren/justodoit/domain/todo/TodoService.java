package com.remreren.justodoit.domain.todo;

import com.remreren.justodoit.domain.todo.models.Todo;
import com.remreren.justodoit.domain.todo.models.TodoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;
    private final TodoMapper mapper;

    public Todo createTodo(Todo todo) {
        TodoEntity ent = mapper.toEntity(todo);
        ent = repository.save(ent);

        return mapper.toModel(ent);
    }

    public List<Todo> listTodos(String user) {
        return repository.findAllByCreatedBy(user)
                         .map(mapper::toModel)
                         .collect(Collectors.toList());
    }
}
