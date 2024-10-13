package com.remreren.justodoit.domain.todo;

import com.remreren.justodoit.domain.todo.models.TodoEntity;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.DynamicProxyable;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface TodoRepository extends CouchbaseRepository<TodoEntity, String>, DynamicProxyable<TodoRepository> {
    Stream<TodoEntity> findAllByCreatedBy(String user);
}
