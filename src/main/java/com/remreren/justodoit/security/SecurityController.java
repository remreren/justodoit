package com.remreren.justodoit.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class SecurityController {

    @GetMapping("/me")
    public String me(@AuthenticationPrincipal UserDetails principal) {
        return "Logged in as, %s".formatted(principal.getUsername());
    }
}
