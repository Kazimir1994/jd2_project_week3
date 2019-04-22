package ru.kazimir.bortnik.repository;

import ru.kazimir.bortnik.repository.model.AuditItem;

import java.sql.Connection;

public interface AuditItemRepository {

    void add(Connection connection, AuditItem auditItem);
}
