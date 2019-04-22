package ru.kazimir.bortnik.springbootmodule.servicedata.converter;

import ru.kazimir.bortnik.springbootmodule.repository.model.Item;
import ru.kazimir.bortnik.springbootmodule.servicedata.model.ItemDTO;

public interface CreateItemConverter {

    Item fromDTO(ItemDTO itemDTO);

    ItemDTO toDTO(Item item);
}
