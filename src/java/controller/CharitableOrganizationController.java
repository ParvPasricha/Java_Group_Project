package controller;

import model.CharitableOrganization;
import model.FoodItem;
import service.CharitableOrganizationService;

public class CharitableOrganizationController {
    private CharitableOrganizationService charitableOrgService;

    public CharitableOrganizationController() {
        this.charitableOrgService = new CharitableOrganizationService();
    }

    public void claimFoodItem(FoodItem item, CharitableOrganization org) {
        charitableOrgService.claimFoodItem(item, org);
    }
}
