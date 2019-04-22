package ru.kazimir.bortnik.repository;

import ru.kazimir.bortnik.repository.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemsRepository {
    Item add(Item item, Connection connection);

    List<Item> getAllItems(Connection connection);

    int update(Long id, String status, Connection connection);
}
