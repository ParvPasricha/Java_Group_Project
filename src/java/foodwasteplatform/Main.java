package foodwasteplatform;

import java.util.Scanner;
import model.CharitableOrganization;
import model.FoodItem;
import service.CharitableOrganizationService;
import service.ConsumerService;
import service.RetailerService;
import service.UserService;
import repository.UserDAO; // Import the repository
import repository.impl.ConcreteUserDAO; // Import your concrete implementation

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize services with a concrete UserDAO implementation
        UserDAO userDAO = new ConcreteUserDAO(); // Use your concrete implementation here
        UserService userService = new UserService(userDAO);
        RetailerService retailerService = new RetailerService();
        CharitableOrganizationService charityService = new CharitableOrganizationService();
        ConsumerService consumerService = new ConsumerService();

        System.out.println("Welcome to the Food Waste Reduction Platform!");
        boolean running = true;

        while (running) {
            System.out.println("1. Register User");
            System.out.println("2. List Food Items");
            System.out.println("3. Add Food Item");
            System.out.println("4. Claim Food Item");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter user type (Retailer/Consumer/CharitableOrganization): ");
                    String userType = scanner.nextLine();
                    userService.registerUser(name, email, password, userType);
                    break;

                case 2:
                    retailerService.listFoodItems();
                    break;

                case 3:
                    System.out.print("Enter food item name: ");
                    String itemName = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter expiration date (YYYY-MM-DD): ");
                    String expirationDate = scanner.nextLine();
                    FoodItem foodItem = new FoodItem(itemName, description, quantity, expirationDate); // Create FoodItem instance
                    retailerService.addFoodItem(foodItem); // Pass FoodItem instance to the service
                    break;

                case 4:
                    System.out.print("Enter food item ID to claim: ");
                    int itemId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter organization name: ");
                    String orgName = scanner.nextLine();
                    CharitableOrganization org = new CharitableOrganization(orgName); // Create CharitableOrganization instance
                    FoodItem item = retailerService.getFoodItemById(itemId); // Retrieve FoodItem instance
                    if (item != null) {
                        charityService.claimFoodItem(item, org);
                    } else {
                        System.out.println("Food item not found.");
                    }
                    break;

                case 5:
                    running = false;
                    System.out.println("Exiting application...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
