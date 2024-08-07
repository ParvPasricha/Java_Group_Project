package service;

import repository.UserDAO;
import model.User;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void registerUser(String username, String email, String password, String userType) {
        // Validate input data
        if (username == null || email == null || password == null || userType == null) {
            throw new IllegalArgumentException("All fields are required for registration.");
        }

        // Check if the user already exists (you might need a method for this in UserDAO)
        if (userExists(username)) {
            throw new IllegalArgumentException("User already exists with the given username.");
        }

        // Create a User object
        User user = new User(username, email, password, userType);

        // Use DAO to persist the user
        userDAO.registerUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getUserType());
    }

    private boolean userExists(String username) {
        // Implement logic to check if a user exists in the database
        // This could be a method in UserDAO like: boolean existsByUsername(String username)
        return false; // Placeholder return; implement actual logic
    }

}
