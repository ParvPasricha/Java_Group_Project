/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class RegisterController {

    /**
     *
     * @param name
     * @param email
     * @param password
     * @param userType
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("userType") String userType) {
        // Registration logic here
        return "registrationSuccess";
    }
}
