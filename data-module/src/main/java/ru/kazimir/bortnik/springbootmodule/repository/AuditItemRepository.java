package ru.kazimir.bortnik.springbootmodule.repository;

import ru.kazimir.bortnik.springbootmodule.repository.model.AuditItem;

import java.sql.Connection;

public interface AuditItemRepository {

    void add(Connection connection, AuditItem auditItem);
}
