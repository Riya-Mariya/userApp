package com.onlinebidding.userApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.onlinebidding"})

public class UserAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAppApplication.class, args);
    }

}
