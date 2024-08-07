/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.FoodItem;
import service.FoodItemService;

public class FoodItemController {
    private FoodItemService foodItemService;

    public FoodItemController() {
        this.foodItemService = new FoodItemService();
    }

    public void addFoodItem(FoodItem item) {
        foodItemService.addFoodItem(item);
    }

    public void updateFoodItem(FoodItem item) {
        foodItemService.updateFoodItem(item);
    }
}
