package ru.kazimir.bortnik.springbootmodule.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.kazimir.bortnik"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
