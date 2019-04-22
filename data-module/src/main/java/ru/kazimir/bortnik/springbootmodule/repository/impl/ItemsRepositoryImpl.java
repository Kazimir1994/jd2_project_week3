package ru.kazimir.bortnik.springbootmodule.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.kazimir.bortnik.springbootmodule.repository.ItemsRepository;
import ru.kazimir.bortnik.springbootmodule.repository.exceptions.ItemRepositoryException;
import ru.kazimir.bortnik.springbootmodule.repository.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemsRepositoryImpl implements ItemsRepository {
    private static final Logger logger = LoggerFactory.getLogger(ItemsRepositoryImpl.class);

    @Override
    public Item add(Item item, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(QueryTypes.ADD.QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getUniqueNumber());
            statement.setString(3, item.getDescription());
            statement.setString(4, item.getStatus());
            statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                logger.info("Add " + item.toString());
                return getItemID(resultSet, item);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(e);
        }
    }

    private Item getItemID(ResultSet resultSet, Item item) {
        try {
            if (resultSet.next()) {
                item.setId(resultSet.getLong(1));
            }
            return item;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(e);
        }
    }

    @Override
    public List<Item> getAllItems(Connection connection) {
        List<Item> itemList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryTypes.GET_All_ITEMS.QUERY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    itemList.add(assemblyOfObjects(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(e);
        }
        return itemList;
    }

    private Item assemblyOfObjects(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getLong("id"));
        item.setName(resultSet.getString("name"));
        item.setStatus(resultSet.getString("status"));
        item.setUniqueNumber(resultSet.getString("unique_number"));
        item.setDescription(resultSet.getString("description"));
        return item;
    }

    @Override
    public int update(Long id, String status, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryTypes.UPDATE_BY_ID.QUERY)) {
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(e);
        }
    }


    private enum QueryTypes {
        UPDATE_BY_ID("UPDATE Items SET status=? WHERE id=?"),
        GET_All_ITEMS("SELECT * FROM Items"),
        ADD("INSERT INTO Items(name, unique_number, description,status) VALUES (?, ?, ?, ?)");

        public String QUERY;
        QueryTypes(String QUERY) {
            this.QUERY = QUERY;
        }

    }
}