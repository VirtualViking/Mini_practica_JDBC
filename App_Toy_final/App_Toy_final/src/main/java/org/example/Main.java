package org.example;

import mapping.exceptions.ToyException;
import mapping.mappers.ToyMapper;
import model.*;
import repositories.ToyRepository;
import repositories.UserRepository;
import repositories.imp.SaleRepositoryImp;
import repositories.imp.ToyRepositoryImp;
import repositories.imp.UserRepositoryImp;
import service.SaleService;
import service.UserService;
import service.imp.SaleServiceImp;
import service.imp.ToyServiceImp;
import service.imp.UserServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletionException;


public class Main {
    private static final ToyRepository toyRepository = new ToyRepositoryImp();

    private static final ToyServiceImp toyService = new ToyServiceImp(toyRepository);

    private static final UserRepository userRepository = new UserRepositoryImp();

    private static final SaleService saleService = new SaleServiceImp(toyService, new SaleRepositoryImp(toyRepository, userRepository));

    private static final ToyMapper toymapper = new ToyMapper();
    private static final Scanner scanner = new Scanner(System.in);

    private static final UserService userService = new UserServiceImp(userRepository);

    public static void main(String[] args) {

        System.out.println("Welcome to Toy Service!");

        boolean exit = false;
        while (!exit) {
            System.out.println("\nToy Service Main Menu");
            System.out.println("1. Add a toy");
            System.out.println("2. Increase amount of a toy");
            System.out.println("3. Decrease amount of a toy");
            System.out.println("4. Get total toys amount");
            System.out.println("5. Get total toys price");
            System.out.println("6. Get most amount toy category");
            System.out.println("7. Get less amount toy category");
            System.out.println("8. Get toys with price greater than");
            System.out.println("9. Sort toys by amount grouped by category");
            System.out.println("10. Get all toys");
            System.out.println("11. Remove toy");
            System.out.println("12. Create an Employee");
            System.out.println("13. Create a Customer");
            System.out.println("14. Get all Users");
            System.out.println("15. Add a sale");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addToy();
                    break;
                case 2:
                    increaseAmount();
                    break;
                case 3:
                    decreaseAmount();
                    break;
                case 4:
                    System.out.println("Total toys amount: " + toyService.getTotalToysAmount());
                    break;
                case 5:
                    System.out.println("Total toys price: " + toyService.getTotalToysPrice());
                    break;
                case 6:
                    System.out.println("Most amount toy category: " + toyService.getMostAmountToyCategory());
                    break;
                case 7:
                    System.out.println("Less amount toy category: " + toyService.getLessAmountToyCategory());
                    break;
                case 8:
                    getToysWithPriceGreaterThan();
                    break;
                case 9:
                    System.out.println("Sorted toys by amount grouped by category: " + toyService.sortToysByAmountGroupedByCategory());
                    break;
                case 10:
                    System.out.println("All toys: ");
                    toyService.getAllToysDb().forEach(t -> System.out.println("Name: " + t.getToyName() + ", Amount: " + t.getToyAmount() + ", Price: " + t.getToyPrice() + ", Category: " + t.getToyCategory()));
                    break;
                case 11:
                    deleteToy();
                    break;
                case 12:
                    createEmployee();
                    break;
                case 13:
                    createCustomer();
                    break;
                case 14:
                    getAllUsers();
                    break;
                case 15:
                    addSale();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void addToy() {
        System.out.print("Enter toy name: ");
        String name = scanner.nextLine();
        System.out.print("Enter toy amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter toy category (0 for FEMENINO, 1 for MASCULINO, 2 for UNISEX): ");
        int category = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Category toyCategory = Category.fromName(category);
        System.out.print("Enter toy price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Toy toy = new Toy(name, amount, toyCategory, price);
        try {
            toyService.addToy(toy);
            System.out.println("Toy added successfully!");
        } catch (ToyException | CompletionException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

    private static void increaseAmount() {
        System.out.print("Enter toy name: ");
        String name = scanner.nextLine();
        System.out.print("Enter amount to increase: ");
        int amount = scanner.nextInt();
        try {
            toyService.increaseAmountToy(name, amount);
            System.out.println("Amount increased successfully!");
        } catch (RuntimeException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

    private static void decreaseAmount() {
        System.out.print("Enter toy name: ");
        String name = scanner.nextLine();
        System.out.print("Enter amount to decrease: ");
        int amount = scanner.nextInt();
        try {
            toyService.decreaseAmountToy(name, amount);
            System.out.println("Amount decreased successfully!");
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void getToysWithPriceGreaterThan() {
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.println("Toys with price greater than " + price + ": ");
        toyService.getToysWithPriceGreaterThan(price).forEach(toy -> {
            System.out.println("Toy: " + toy.getToyName() + ", " + " Price: " + toy.getToyPrice());
        });
    }

    private static void deleteToy() {
        System.out.print("Enter toy name: ");
        String name = scanner.nextLine();
        try {
            toyService.deleteToyByName(name);
            System.out.println("Toy deleted successfully!");
        } catch (RuntimeException exception) {
            System.out.println("ERROR: " +  exception.getMessage());
        }
    }

    private static void createEmployee() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        User employee = new User(name, "employee");
        try {
            userService.save(employee);
            System.out.println("Employee created successfully!");
        } catch (RuntimeException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }
    private static void createCustomer() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        User customer = new User(name, "customer");
        try {
            userService.save(customer);
            System.out.println("Customer created successfully!");
        } catch (RuntimeException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }
    private static void getAllUsers() {
        System.out.println("All users: ");
        userService.findAll().forEach(u -> System.out.println("Name: " + u.getName() + ", Role: " + u.getRole()));
    }
    private static void addSale() {

        System.out.print("Enter customer id: ");
        Long customerId = scanner.nextLong();
        System.out.println("Enter employee id: ");
        Long employeeId = scanner.nextLong();

        System.out.println("How many toys are you going to buy?");
        int amount = scanner.nextInt();
        List<SaleDetail> saleDetails = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            System.out.println("Enter toy id: ");
            Long toyId = scanner.nextLong();
            System.out.println("Enter toy amount: ");
            int toyAmount = scanner.nextInt();
            Toy toy = toyService.getToyById(toyId);
            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setToy(toy);
            saleDetail.setQuantity(toyAmount);
            saleDetails.add(saleDetail);
        }
        Sale sale = new Sale();
        User customer = new User();
        customer.setId(customerId);
        User employee = new User();
        employee.setId(employeeId);
        sale.setCustomer(customer);
        sale.setEmployee(employee);
        sale.setSaleDetails(saleDetails);
        try {
            saleService.addSale(sale);
            System.out.println("Sale added successfully!");
        } catch (RuntimeException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }
}