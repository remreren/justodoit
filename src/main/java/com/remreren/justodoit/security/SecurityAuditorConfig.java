package com.remreren.justodoit.security;


import com.querydsl.core.annotations.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing;
import org.springframework.stereotype.Component;

@Config
@Component
@EnableCouchbaseAuditing
public class SecurityAuditorConfig {

    @Bean
    public SecurityAuditorAware auditorAware() {
        return new SecurityAuditorAware();
    }
}
