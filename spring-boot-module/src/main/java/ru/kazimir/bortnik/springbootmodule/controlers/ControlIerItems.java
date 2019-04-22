
package ru.kazimir.bortnik.springbootmodule.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kazimir.bortnik.repository.model.enums.StatusItems;
import ru.kazimir.bortnik.servicedata.ItemService;
import ru.kazimir.bortnik.servicedata.model.ItemDTO;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ControlIerItems {

    private static final Logger logger = LoggerFactory.getLogger(ControlIerItems.class);
    private ItemService itemService;

    @Autowired
    public ControlIerItems(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("/items")
    public String showAllItems(ItemDTO itemDTO, Model model, BindingResult results) {
        List<ItemDTO> items = itemService.getItems();
        model.addAttribute("items", items);
        model.addAttribute("statusItems", StatusItems.values());
        if (model.containsAttribute("error")) {
            for (ObjectError error : ((List<ObjectError>) model.asMap().get("error"))) {
                results.addError(error);
            }
        }
        return "items";
    }

    @PostMapping("/updateStatusItems")
    public String updateStatusItems(@Valid ItemDTO itemDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/items";
        }
        logger.info(String.format("Update Item {Id=%s, Status=%s}", itemDTO.getId(), itemDTO.getStatus()));
        itemService.update(itemDTO.getId(), itemDTO.getStatus());
        return "redirect:/items";
    }

    @PostMapping("/createItems")
    public String createItem(@Valid ItemDTO itemDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/items";
        }
        itemService.add(itemDTO);
        logger.info(String.format("Create Item {name= %s, Description= %s}", itemDTO.getName(), itemDTO.getDescription()));
        return "redirect:/items";
    }
}