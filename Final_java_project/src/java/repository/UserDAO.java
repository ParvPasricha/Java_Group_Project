package repository;

public interface UserDAO {
    void registerUser(String username, String email, String password, String userType);
    // Additional methods as needed
}
