package com.remreren.justodoit.domain.auth;

import com.remreren.justodoit.contract.AuthControllerContract;
import com.remreren.justodoit.security.JwtService;
import com.remreren.justodoit.domain.user.UserService;
import com.remreren.justodoit.domain.auth.models.AuthenticationModel;
import com.remreren.justodoit.domain.user.models.User;
import com.remreren.justodoit.domain.user.models.UserWithToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerContract {

    private final UserService service;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public UserWithToken register(@RequestBody @Validated AuthenticationModel request) {
        User toCreate = new User(null, request.email(), request.password());
        User created = service.createUser(toCreate);

        String token = jwtService.generateToken(created);

        return new UserWithToken(created, token);
    }

    @PostMapping("/login")
    public UserWithToken login(@RequestBody @Validated AuthenticationModel request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),
                                                        request.password()));

        User user = service.getUser(new User(null, request.email(), request.password()));
        String token = jwtService.generateToken(user);

        return new UserWithToken(user, token);
    }
}
