/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



import java.util.List;

public class AlertSubscription {
    private String location;
    private String communicationMethod; // email or phone
    private List<String> foodPreferences;

    public AlertSubscription(String location, String communicationMethod, List<String> foodPreferences) {
        this.location = location;
        this.communicationMethod = communicationMethod;
        this.foodPreferences = foodPreferences;
    }

    // Getters and Setters
}
