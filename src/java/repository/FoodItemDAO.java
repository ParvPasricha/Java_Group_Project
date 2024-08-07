/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;


import java.util.List;
import model.FoodItem;

public interface FoodItemDAO {
    void addFoodItem(FoodItem item);
    FoodItem getFoodItemById(int id);
    List<FoodItem> getAllFoodItems();
    void updateFoodItem(FoodItem item);
    void deleteFoodItem(int id);

    public void saveFoodItem(FoodItem item);

    public List<FoodItem> getSurplusItems();
}
