package ru.kazimir.bortnik.springbootmodule.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.kazimir.bortnik.springbootmodule.repository.AuditItemRepository;
import ru.kazimir.bortnik.springbootmodule.repository.constant.ErrorMessagesRepository;
import ru.kazimir.bortnik.springbootmodule.repository.exceptions.ItemRepositoryException;
import ru.kazimir.bortnik.springbootmodule.repository.model.AuditItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class AuditItemRepositoryImpl implements AuditItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(AuditItemRepositoryImpl.class);

    @Override
    public void add(Connection connection, AuditItem auditItem) {
        try (PreparedStatement statement = connection.prepareStatement(QueryTypes.ADD_UPDATE_LOG.QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, auditItem.getAction());
            statement.setLong(2, auditItem.getItemID());
            statement.setString(3, auditItem.getUpdateStatus());
            statement.setTimestamp(4, auditItem.getDate());
            int numberOfRowsAdded = statement.executeUpdate();
            if (numberOfRowsAdded == 0) {
                throw new SQLException(ErrorMessagesRepository.NO_RECORD);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(e);
        }
    }

    private enum QueryTypes {
        ADD_UPDATE_LOG("INSERT INTO Audit_Item(action, item_id, update_Status,date) VALUES (?, ?, ?, ?)");
        public String QUERY;

        QueryTypes(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
