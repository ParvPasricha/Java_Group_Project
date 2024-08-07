/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Consumer;
import model.FoodItem;
import service.ConsumerService;


public class ConsumerController {
    private ConsumerService consumerService;

    public ConsumerController() {
        this.consumerService = new ConsumerService();
    }

    public void purchaseFoodItem(FoodItem item, Consumer consumer) {
        consumerService.purchaseFoodItem(item, consumer);
    }
}

