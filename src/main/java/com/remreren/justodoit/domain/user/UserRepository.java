package com.remreren.justodoit.domain.user;

import com.remreren.justodoit.domain.user.models.UserEntity;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CouchbaseRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
