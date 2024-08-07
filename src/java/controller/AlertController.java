/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import service.AlertService;
import model.AlertSubscription;

public class AlertController {
    private AlertService alertService;

    public AlertController() {
        this.alertService = new AlertService();
    }

    public void subscribeUser(AlertSubscription subscription) {
        alertService.subscribeUser(subscription);
    }
}
