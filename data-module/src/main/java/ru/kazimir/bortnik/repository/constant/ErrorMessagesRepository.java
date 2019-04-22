package ru.kazimir.bortnik.repository.constant;

public class ErrorMessagesRepository {
    public static final String NO_RECORD = "Failed to write to the database information about the change of the item";
    public static final String NO_CONNECT_JDBC_DRIVE = "Failed to connect jdbc driver";
    public static final String NO_CONNECTION = "Failed to get connection";
    public static final String NO_CONFIGURATION_FILE = "File for setting tables in database not found";

    private ErrorMessagesRepository() {
    }
}
