package com.remreren.justodoit;

import org.springframework.boot.SpringApplication;

public class TestJustodoitApplication {

    public static void main(String[] args) {
        SpringApplication.from(JustodoitApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
