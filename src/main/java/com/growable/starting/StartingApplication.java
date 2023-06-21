package com.growable.starting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StartingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartingApplication.class, args);
    }

}
