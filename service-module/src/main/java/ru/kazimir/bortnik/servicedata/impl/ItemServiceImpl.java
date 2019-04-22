package ru.kazimir.bortnik.servicedata.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kazimir.bortnik.servicedata.ItemService;
import ru.kazimir.bortnik.servicedata.converter.CreateItemConverter;
import ru.kazimir.bortnik.servicedata.exceptions.ItemServiceException;
import ru.kazimir.bortnik.servicedata.model.ItemDTO;
import ru.kazimir.bortnik.repository.ItemsRepository;
import ru.kazimir.bortnik.repository.connection.ConnectionDataBseService;
import ru.kazimir.bortnik.repository.exceptions.ItemRepositoryException;
import ru.kazimir.bortnik.repository.model.Item;
import ru.kazimir.bortnik.servicedata.constant.ErrorMessagesService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final CreateItemConverter createItemConverter;
    private final ItemsRepository itemsRepository;
    private final ConnectionDataBseService connectionDataBseService;

    @Autowired
    public ItemServiceImpl(CreateItemConverter createItemConverter, ItemsRepository itemsRepository, ConnectionDataBseService connectionDataBseService) {
        this.createItemConverter = createItemConverter;
        this.itemsRepository = itemsRepository;
        this.connectionDataBseService = connectionDataBseService;
    }

    private static final String STATUS_DEFAULT = "READY";

    @Override
    public ItemDTO add(ItemDTO itemDTO) {
        itemDTO.setUniqueNumber(UUID.randomUUID().toString());
        itemDTO.setStatus(STATUS_DEFAULT);
        Item item = createItemConverter.fromDTO(itemDTO);
        try (Connection connection = connectionDataBseService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Item savedItem = itemsRepository.add(item, connection);
                connection.commit();
                return createItemConverter.toDTO(savedItem);
            } catch (SQLException | ItemRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ItemServiceException(ErrorMessagesService.QUERY_FAILED_ERROR, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemServiceException(ErrorMessagesService.NO_CONNECTION, e);
        }
    }

    @Override
    public List<ItemDTO> getItems() {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        try (Connection connection = connectionDataBseService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<Item> itemList = itemsRepository.getAllItems(connection);
                for (Item item : itemList) {
                    itemDTOList.add(createItemConverter.toDTO(item));
                }
                connection.commit();
                return itemDTOList;
            } catch (SQLException | ItemRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ItemServiceException(ErrorMessagesService.QUERY_FAILED_ERROR, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemServiceException(ErrorMessagesService.NO_CONNECTION, e);
        }
    }

    @Override
    public int update(Long id, String status) {
        try (Connection connection = connectionDataBseService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                int sizeUpdate = itemsRepository.update(id, status, connection);
                connection.commit();
                return sizeUpdate;
            } catch (SQLException | ItemRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ItemServiceException(ErrorMessagesService.QUERY_FAILED_ERROR, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemServiceException(ErrorMessagesService.NO_CONNECTION, e);
        }
    }
}
