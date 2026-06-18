package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@EnableScheduling
public class TourismManagementApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TourismManagementApplication.class, args);
        System.out.println("Tourism Management API Started Successfully!");
    }
}
