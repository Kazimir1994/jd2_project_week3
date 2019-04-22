package ru.kazimir.bortnik.springbootmodule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:configDatabase.properties")
})
@ComponentScan(basePackages = {"ru.kazimir.bortnik"})
public class AppConfig {

}
