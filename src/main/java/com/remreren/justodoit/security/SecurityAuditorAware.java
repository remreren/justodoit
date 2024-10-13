package com.remreren.justodoit.security;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class SecurityAuditorAware implements AuditorAware<String> {

    @Override
    @Nonnull
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                       .map(SecurityContext::getAuthentication)
                       .filter(Authentication::isAuthenticated)
                       .map(Authentication::getPrincipal)
                       .filter(principal -> principal instanceof User)
                       .map(User.class::cast)
                       .map(User::getUsername);
    }
}
