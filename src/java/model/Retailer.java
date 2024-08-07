package model;

// Assuming there is a User class that Retailer extends
public class Retailer extends User {
    // Additional fields specific to retailers can be added here

    // Constructor
    public Retailer(String username, String email, String password) {
        super(username, email, password, "retailer");
        // Initialize any retailer-specific fields here if needed
    }

    // Additional methods specific to Retailer
    // For example, managing inventory, listing surplus items, etc.
}
