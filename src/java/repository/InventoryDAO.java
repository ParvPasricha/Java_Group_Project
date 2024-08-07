/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;


import java.util.List;
import model.FoodItem;

public interface InventoryDAO {
    List<FoodItem> getInventoryForRetailer(int retailerId);
    void updateInventory(int retailerId, FoodItem item);
}
