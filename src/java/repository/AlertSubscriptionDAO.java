/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;


import java.util.List;
import model.AlertSubscription;
public interface AlertSubscriptionDAO {
    void subscribeUser(AlertSubscription subscription);
    List<AlertSubscription> getSubscriptionsForUser(int userId);
    void updateSubscription(AlertSubscription subscription);
    void deleteSubscription(int id);

    public void saveSubscription(AlertSubscription subscription);
}

