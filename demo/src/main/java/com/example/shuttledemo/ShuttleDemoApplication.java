package com.example.shuttledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example","me.ezerror"})
public class ShuttleDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShuttleDemoApplication.class, args);
    }

}   
