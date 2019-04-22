package ru.kazimir.bortnik.springbootmodule.repository.connection;

import java.sql.Connection;

public interface ConnectionDataBseService {
    Connection getConnection();
}
