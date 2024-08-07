/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



public class CharitableOrganization extends User {
    public CharitableOrganization(String name, String email, String password) {
        super(name, email, password, UserType.CHARITABLE_ORG);
    }

    // Charitable Organization-specific methods
}
