package ru.kazimir.bortnik.springbootmodule.repository.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseProperties {

    @Value("${spring.datasource.url}")
    private String databaseURL;

    @Value("${spring.datasource.driver-class-name}")
    private String databaseDriverName;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${database.config.name}")
    private String databaseConfigName;


    public String getDatabaseURL() {
        return databaseURL;
    }

    public String getDatabaseDriverName() {
        return databaseDriverName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabaseConfigName() {
        return databaseConfigName;
    }
}
