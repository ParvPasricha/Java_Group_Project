package controller;

import java.util.List;
import model.FoodItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.RetailerService;

@Controller
@RequestMapping("/retailer")
public class RetailerController {

    private final RetailerService retailerService;

    @Autowired
    public RetailerController(RetailerService retailerService) {
        this.retailerService = retailerService;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("register");
    }

    @PostMapping("/addFoodItem")
    public String addFoodItem(@RequestParam("description") String description,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("expirationDate") String expirationDate) {
        FoodItem item = new FoodItem(description, expirationDate, quantity, expirationDate);
        retailerService.addFoodItem(item);
        return "redirect:/retailer/list";
    }

    @GetMapping("/list")
    public ModelAndView listSurplusItems() {
        List<FoodItem> items = retailerService.listSurplusItems();
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("items", items);
        return modelAndView;
    }
}
