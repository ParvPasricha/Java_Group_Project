package model;

// Assuming Consumer extends User, if User is a base class for all users
public class Consumer extends User {
    // Additional fields specific to consumers can be added here if needed

    // Constructor
    public Consumer(String username, String email, String password) {
        super(username, email, password, "consumer");
        // Initialize any consumer-specific fields here
    }

    // Consumer-specific methods can be added here if needed
}
