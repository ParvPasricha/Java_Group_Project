/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.AlertSubscription;
import model.FoodItem;
import repository.AlertSubscriptionDAO;


public class AlertService {
    private AlertSubscriptionDAO alertDAO;

    public AlertService() {
        this.alertDAO = new AlertSubscriptionDAO() {
            @Override
            public void subscribeUser(AlertSubscription subscription) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public List<AlertSubscription> getSubscriptionsForUser(int userId) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void updateSubscription(AlertSubscription subscription) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void deleteSubscription(int id) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void saveSubscription(AlertSubscription subscription) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
    }

    public void subscribeUser(AlertSubscription subscription) {
        alertDAO.saveSubscription(subscription);
    }

    public void sendAlerts(FoodItem item) {
        // Logic to send alerts
    }
}
