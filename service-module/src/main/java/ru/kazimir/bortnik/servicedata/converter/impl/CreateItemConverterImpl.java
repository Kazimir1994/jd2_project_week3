package ru.kazimir.bortnik.servicedata.converter.impl;

import org.springframework.stereotype.Component;
import ru.kazimir.bortnik.repository.model.Item;
import ru.kazimir.bortnik.servicedata.converter.CreateItemConverter;
import ru.kazimir.bortnik.servicedata.model.ItemDTO;

@Component
public class CreateItemConverterImpl implements CreateItemConverter {
    @Override
    public Item fromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setUniqueNumber(itemDTO.getUniqueNumber());
        item.setStatus(itemDTO.getStatus());
        return item;
    }

    @Override
    public ItemDTO toDTO(Item Item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(Item.getId());
        itemDTO.setName(Item.getName());
        itemDTO.setDescription(Item.getDescription());
        itemDTO.setUniqueNumber(Item.getUniqueNumber());
        itemDTO.setStatus(Item.getStatus());
        return itemDTO;
    }
}
