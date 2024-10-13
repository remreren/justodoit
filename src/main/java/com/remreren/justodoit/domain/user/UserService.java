package com.remreren.justodoit.domain.user;

import com.remreren.justodoit.domain.user.models.User;
import com.remreren.justodoit.domain.user.models.UserEntity;
import com.remreren.justodoit.exception.BaseException;
import com.remreren.justodoit.exception.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        repository.findByEmail(user.email())
                .ifPresent(ent -> {
                    throw BaseException.of(ErrorMessages.USER_ALREADY_EXISTS);
                });

        String password = passwordEncoder.encode(user.password());

        UserEntity ent = mapper.toEntity(new User(null, user.email(), password));
        ent = repository.save(ent);

        return mapper.toModel(ent);
    }

    @Transactional(readOnly = true)
    public User getUser(User user) {
        return repository.findByEmail(user.email())
                         .map(mapper::toModel)
                         .orElseThrow(() -> BaseException.of(ErrorMessages.USER_NOT_FOUND));
    }
}
