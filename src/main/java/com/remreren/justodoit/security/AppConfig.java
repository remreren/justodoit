package com.remreren.justodoit.security;

import com.remreren.justodoit.domain.user.models.UserEntity;
import com.remreren.justodoit.domain.user.UserRepository;
import com.remreren.justodoit.exception.BaseException;
import com.remreren.justodoit.exception.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserRepository userRepository;

    @Bean
    UserDetailsService userDetailsService() {
        Function<UserEntity, UserDetails> map = user -> new User(user.getEmail(),
                                                                 user.getPassword(),
                                                                 List.of());

        return username -> userRepository.findByEmail(username)
                                         .map(map)
                                         .orElseThrow(() -> BaseException.of(ErrorMessages.USER_NOT_FOUND));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
