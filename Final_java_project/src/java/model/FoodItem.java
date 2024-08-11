/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



import java.time.LocalDate;

public class FoodItem {
    private String id;
    private String name;
    private int quantity;
    private LocalDate expirationDate;
    private boolean isSurplus;

    // Constructor
    public FoodItem(String id, String name, int quantity, String expirationDate1) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.isSurplus = checkSurplus();
    }

    private boolean checkSurplus() {
        return expirationDate.isBefore(LocalDate.now().plusDays(7));
    }

    // Getters and Setters
}

