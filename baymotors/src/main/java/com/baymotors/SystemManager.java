package com.baymotors;

import java.util.*;

public class SystemManager {
    private List<Customer> customers;
    private List<Mechanic> mechanics;
    private List<Task> tasks;
    private List<Vehicle> vehicles;
    private Map<String, List<String>> manufacturers;
    private Manager manager;

    private String loggedInUser; // Track logged-in user
    private String loggedInRole; // Track role: "Manager" or "Mechanic"
    
    private static SystemManager systemManager = null;

    private SystemManager() {
        customers = new ArrayList<>();
        mechanics = new ArrayList<>();
        tasks = new ArrayList<>();
        vehicles = new ArrayList<>();
        manufacturers = new HashMap<>();
        manager = new Manager(1, "Alice", "alice@baymotors.com", "1234567890");

        // Add mechanics
        mechanics.add(new Mechanic(1, "Bob", "bob@baymotors.com", "9876543210"));
        mechanics.add(new Mechanic(2, "Charlie", "charlie@baymotors.com", "8765432109"));
    }
    
    public static SystemManager getInstance() {
    	if(systemManager == null) {
    		systemManager = new SystemManager();
    	}
    	
    	return systemManager;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (loggedInUser == null) {
                mainMenu(scanner);
            } else if ("Manager".equals(loggedInRole)) {
                managerMenu(scanner);
            } else if ("Mechanic".equals(loggedInRole)) {
                mechanicMenu(scanner);
            }
        }
    }

    private void mainMenu(Scanner scanner) {
        System.out.println("\nWelcome to Bay Motors System");
        System.out.println("1. Log In");
        System.out.println("2. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            login(scanner);
        } else if (choice == 2) {
            System.out.println("Exiting... Goodbye!");
            System.exit(0);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private void login(Scanner scanner) {
        System.out.print("Enter your name to login: ");
        String name = scanner.nextLine();

        if (name.equals(manager.getName())) {
            loggedInUser = name;
            loggedInRole = "Manager";
            System.out.println("Welcome, Manager " + name + "!");
        } else {
            for (Mechanic mechanic : mechanics) {
                if (mechanic.getName().equals(name)) {
                    loggedInUser = name;
                    loggedInRole = "Mechanic";
                    System.out.println("Welcome, Mechanic " + name + "!");
                    return;
                }
            }
            System.out.println("Name not recognized. Please try again.");
        }
    }

    private void logout() {
        System.out.println("Logging out " + loggedInUser + "...");
        loggedInUser = null;
        loggedInRole = null;
    }

    private void managerMenu(Scanner scanner) {
        System.out.println("\nManager Menu");
        System.out.println("1. Log Customer & Vehicle Details");
        System.out.println("2. Upgrade Customer to Registered");
        System.out.println("3. Allocate Task to Mechanic");
        System.out.println("4. View All Tasks with Assignee");
        System.out.println("5. Post Notifications");
        System.out.println("6. Add Manufacturer or Supplier");
        System.out.println("7. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 -> addWalkInCustomer(scanner);
            case 2 -> upgradeCustomer(scanner);
            case 3 -> allocateTaskToMechanic(scanner);
            case 4 -> viewAllTasks();
            case 5 -> postNotifications(scanner);
            case 6 -> manageManufacturerOrSupplier(scanner);
            case 7 -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    private void mechanicMenu(Scanner scanner) {
        System.out.println("\nMechanic Menu");
        System.out.println("1. Add New Customer & Vehicle");
        System.out.println("2. Mark Task as Complete");
        System.out.println("3. View My Tasks");
        System.out.println("4. Upgrade Customer to Registered");
        System.out.println("5. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
	        switch (choice) {
	            case 1 -> addWalkInCustomer(scanner);
	            case 2 -> markTaskAsComplete(scanner);
	            case 3 -> viewMyTasks();
	            case 4 -> upgradeCustomer(scanner);
	            case 5 -> logout();
	            default -> System.out.println("Invalid choice.");
	        }
        } catch (NotFoundException e) {
			System.out.println(e.getMessage());
		}
    }

    private void viewAllTasks() {
        System.out.println("\nAll Tasks:");
        tasks.forEach(task -> {
            String assignee = mechanics.stream()
                    .filter(mechanic -> mechanic.getTasks().contains(task))
                    .map(Mechanic::getName)
                    .findFirst()
                    .orElse("Unassigned");
            System.out.println(task + " | Assigned to: " + assignee);
        });
    }

    private void viewMyTasks() {
        System.out.println("\nMy Tasks:");
        mechanics.stream()
                .filter(mechanic -> mechanic.getName().equals(loggedInUser))
                .flatMap(mechanic -> mechanic.getTasks().stream())
                .sorted(Comparator.comparingInt(Task::getPriority).reversed())
                .forEach(System.out::println);
    }



    
    private void postNotifications(Scanner scanner) {
        System.out.println("Post Notifications Menu:");
        System.out.println("1. Notify Registered Customers");
        System.out.println("2. Notify Unregistered Customers");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Notification Message: ");
        String message = scanner.nextLine();

        switch (choice) {
            case 1:
                manager.notifyCustomers(customers, message);
                break;
            case 2:
                manager.notifyUnregisteredCustomers(customers, message);
                break;
            default:
                System.out.println("Invalid choice. Please select 1 or 2.");
        }
    }


    private void addWalkInCustomer(Scanner scanner) {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Customer Phone: ");
        String phone = scanner.nextLine();

        Customer customer = new Customer(customers.size() + 1, name, email, phone, false);
        customers.add(customer);

        System.out.print("Enter Vehicle Registration Number: ");
        String regNumber = scanner.nextLine();
        System.out.print("Enter Vehicle Make: ");
        String make = scanner.nextLine();
        System.out.print("Enter Vehicle Model: ");
        String model = scanner.nextLine();

        Vehicle vehicle = new Vehicle(vehicles.size() + 1, regNumber, make, model, customer);
        vehicles.add(vehicle);
        customer.addVehicle(vehicle);

        System.out.println("Walk-in customer and vehicle details added.");
    }

    private void upgradeCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID to upgrade: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Customer customer = findCustomerById(customerId);
        if (customer != null && !customer.isRegistered()) {
            customer.register();
            System.out.println("Customer " + customer.getName() + " upgraded to registered.");
        } else {
            System.out.println("Customer not found or already registered.");
        }
    }

    private void allocateTaskToMechanic(Scanner scanner) {
        System.out.print("Enter Mechanic ID: ");
        int mechanicId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Mechanic mechanic = findMechanicById(mechanicId);
        if (mechanic == null) {
            System.out.println("Mechanic not found.");
            return;
        }

        System.out.print("Enter Vehicle Registration Number for Task: ");
        String regNumber = scanner.nextLine();

        Vehicle vehicle = findVehicleByRegistration(regNumber);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Enter Task Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Task Priority: ");
        int priority = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Task task = new Task(tasks.size() + 1, description, priority);
        tasks.add(task);
        manager.allocateTask(task, mechanic);

        System.out.println("Task allocated successfully to Mechanic " + mechanic.getName());
    }

    private void markTaskAsComplete(Scanner scanner) throws NotFoundException {
        System.out.print("Enter Task ID: ");
        int taskId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Task task = findTaskById(taskId);
        if (task != null) {
            task.markComplete();

            Vehicle vehicle = findVehicleByTask(task);
            if (vehicle != null) {
                Customer customer = vehicle.getOwner();
                System.out.println("Notification sent to " + customer.getName() + ": Your vehicle is ready for pickup.");
            }
        } else {
            throw new NotFoundException("Task");
        }
    }

    private void manageManufacturerOrSupplier(Scanner scanner) {
        System.out.print("Enter Manufacturer Name: ");
        String manufacturer = scanner.nextLine();
        manufacturers.putIfAbsent(manufacturer, new ArrayList<>());

        System.out.print("Add Supplier for this Manufacturer (yes/no)? ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter Supplier Name: ");
            String supplier = scanner.nextLine();
            manufacturers.get(manufacturer).add(supplier);
            System.out.println("Supplier added for Manufacturer: " + manufacturer);
        }
    }

    // Helper methods to find customers, mechanics, tasks, and vehicles
    private Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) return customer;
        }
        return null;
    }

    private Mechanic findMechanicById(int id) {
        for (Mechanic mechanic : mechanics) {
            if (mechanic.getId() == id) return mechanic;
        }
        return null;
    }

    private Vehicle findVehicleByRegistration(String regNumber) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getRegistrationNumber().equals(regNumber)) return vehicle;
        }
        return null;
    }

    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getTaskId() == id) return task;
        }
        return null;
    }

    private Vehicle findVehicleByTask(Task task) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getOwner().getVehicles().contains(vehicle)) return vehicle;
        }
        return null;
    }
    
    private void viewMechanicTasks(Scanner scanner) {
        System.out.print("Enter Mechanic ID: ");
        int mechanicId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Mechanic mechanic = findMechanicById(mechanicId);
        if (mechanic == null) {
            System.out.println("Mechanic not found.");
            return;
        }

        List<Task> mechanicTasks = mechanic.getTasks();
        if (mechanicTasks.isEmpty()) {
            System.out.println("No tasks assigned to Mechanic " + mechanic.getName());
        } else {
            System.out.println("Tasks for Mechanic " + mechanic.getName() + ":");
            mechanicTasks.stream()
                         .sorted(Comparator.comparingInt(Task::getPriority).reversed()) // Sort by priority
                         .forEach(task -> System.out.println(task));
        }
    }

}
