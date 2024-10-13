package com.remreren.justodoit.domain.todo;

import com.remreren.justodoit.domain.todo.models.CreateTodo;
import com.remreren.justodoit.domain.todo.models.Todo;
import com.remreren.justodoit.domain.todo.models.TodoEntity;
import com.remreren.justodoit.domain.todo.models.UpdateTodo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TodoMapper {
    TodoEntity toEntity(Todo todo);

    Todo toModel(CreateTodo request);
    Todo toModel(UpdateTodo request, String id);
    Todo toModel(TodoEntity entity);
}
