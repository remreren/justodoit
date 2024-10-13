package com.remreren.justodoit.contract;

import com.remreren.justodoit.domain.todo.models.CreateTodo;
import com.remreren.justodoit.domain.todo.models.Todo;
import com.remreren.justodoit.domain.todo.models.TodoList;
import com.remreren.justodoit.domain.todo.models.UpdateTodo;
import com.remreren.justodoit.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface TodoControllerContract {

    @Operation(
            summary = "List all todos",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TodoList.class))),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ResponseEntity<TodoList> listTodos(UserDetails principal);

    @Operation(
            summary = "Create a new todo",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = CreateTodo.class))),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Todo.class))),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ResponseEntity<Todo> createTodo(CreateTodo request);

    // patch and put
    @Operation(
            summary = "Update a todo, full update",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UpdateTodo.class))),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Todo.class))),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ResponseEntity<Todo> updateTodo(String id, UpdateTodo todo, UserDetails principal);

    @Operation(
            summary = "Patch a todo, for partial update",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UpdateTodo.class))),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Todo.class))),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ResponseEntity<Todo> patchTodo(String id, UpdateTodo todo, UserDetails principal);
}
