package ru.kazimir.bortnik.springbootmodule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.kazimir.bortnik.springbootmodule.controlers.ControlIerItems;
import ru.kazimir.bortnik.servicedata.ItemService;
import ru.kazimir.bortnik.servicedata.model.ItemDTO;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ControlIerItemsTest {

    @Mock
    private ItemService itemService;
    private ControlIerItems itemController;
    private MockMvc mockMvc;

    private List<ItemDTO> items = Arrays.asList(new ItemDTO("name", "qweqweqwe", "READY", "READY", 1L),
            new ItemDTO("name", "qweqweqwe", "READY", "COMPLETED", 2L));

    private ItemDTO itemDTO = new ItemDTO("name", "qweqweqwe", "READY", "COMPLETED", 2L);

    @Before
    public void init() {
        itemController = new ControlIerItems(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        when(itemService.getItems()).thenReturn(items);
    }


    @Test
    public void requestForItemsIsSuccessfullyProcessedWithAvailableItemsList() throws Exception {
        this.mockMvc.perform(get("/items.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("items", equalTo(items)))
                .andExpect(forwardedUrl("items"));
    }

    @Test
    public void allItemsAreAddedToModelForItemsView() {
        Model model = new ExtendedModelMap();
        BindingResult bindingResult=null;
        String items = itemController.showAllItems(itemDTO,model,bindingResult);
        assertThat(items, equalTo("items"));
        assertThat(model.asMap(), hasEntry("items", this.items));
    }


}

