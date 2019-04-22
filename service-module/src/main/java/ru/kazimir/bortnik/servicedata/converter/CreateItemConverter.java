package ru.kazimir.bortnik.servicedata.converter;

import ru.kazimir.bortnik.repository.model.Item;
import ru.kazimir.bortnik.servicedata.model.ItemDTO;

public interface CreateItemConverter {

    Item fromDTO(ItemDTO itemDTO);

    ItemDTO toDTO(Item item);
}
