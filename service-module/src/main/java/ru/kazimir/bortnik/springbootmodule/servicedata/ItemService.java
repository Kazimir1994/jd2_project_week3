package ru.kazimir.bortnik.springbootmodule.servicedata;

import ru.kazimir.bortnik.springbootmodule.servicedata.model.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO add(ItemDTO itemDTO);

    List<ItemDTO> getItems();

    int update(Long id, String status);
}
