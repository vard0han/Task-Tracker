package com.example.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskTrackerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TaskTrackerApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE); // Disable the web server
        app.run(args);
    }

}
