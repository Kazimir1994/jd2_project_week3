package ru.kazimir.bortnik.springbootmodule.repository.connection.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kazimir.bortnik.springbootmodule.repository.connection.ConnectionDataBseService;
import ru.kazimir.bortnik.springbootmodule.repository.exceptions.ConnectionDataBaseExceptions;
import ru.kazimir.bortnik.springbootmodule.repository.properties.DatabaseProperties;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ru.kazimir.bortnik.springbootmodule.repository.constant.ErrorMessagesRepository.*;

@Service
public class ConnectionDataBaseServiceImpl implements ConnectionDataBseService {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionDataBaseServiceImpl.class);
    private DatabaseProperties databaseProperties;

    @Autowired
    public ConnectionDataBaseServiceImpl(
            DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        try {
            System.out.println(databaseProperties.getDatabaseDriverName());
            Class.forName(databaseProperties.getDatabaseDriverName());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionDataBaseExceptions(NO_CONNECT_JDBC_DRIVE, e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(databaseProperties.getDatabaseURL(),
                    databaseProperties.getDatabaseUsername(),
                    databaseProperties.getDatabasePassword());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionDataBaseExceptions(NO_CONNECTION, e);
        }
    }

    @PostConstruct
    public void databaseInitialization() {
        File configurationFile = new File(getClass().getResource("/" + databaseProperties.getDatabaseConfigName()).getPath());
        if (configurationFile.exists()) {
            List<String> SetOfScripts = returnASetOfScripts(configurationFile);
            try (Connection connection = getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    for (String scripts : SetOfScripts) {
                        statement.execute(scripts);
                        logger.info(scripts);
                    }
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new ConnectionDataBaseExceptions(NO_CONNECTION, e);
            }
        } else {
            throw new ConnectionDataBaseExceptions(NO_CONFIGURATION_FILE);
        }
    }

    private List<String> returnASetOfScripts(File nameFile) {
        StringBuilder dataFile = new StringBuilder();
        List<String> commandList = new ArrayList<>();
        boolean commandStartKey = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nameFile))) {
            String dataLine = bufferedReader.readLine();
            if (dataLine != null) {
                if (dataLine.contains("CREATE TABLE") || dataLine.contains("INSERT INTO") || dataLine.contains("DROP TABLE")) {
                    commandStartKey = true;
                    dataFile.append(dataLine).append('\n');
                }
                while ((dataLine = bufferedReader.readLine()) != null) {
                    if (commandStartKey) {
                        dataFile.append(dataLine);
                        if (dataLine.contains(";")) {
                            commandStartKey = false;
                            commandList.add(dataFile.toString());
                            dataFile.delete(0, dataFile.length());
                        } else {
                            dataFile.append('\n');
                        }
                    } else {
                        if (dataLine.contains("CREATE TABLE") || dataLine.contains("INSERT INTO") || dataLine.contains("DROP TABLE")) {
                            dataFile.append(dataLine);
                            commandStartKey = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionDataBaseExceptions(NO_CONFIGURATION_FILE);
        }
        return commandList;
    }
}
