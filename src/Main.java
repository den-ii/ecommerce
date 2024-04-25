import com.ecommerce.Customer;
import com.ecommerce.Product;
import com.ecommerce.orders.Order;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class - runs the ecommerce package.
 *
 * @author Deni Wisdom Ohiche
 */
public class Main {

    /**
     * Entry point to the program.
     * @param args cmd_line arguments
     */
    public static void main(String[] args) {
        String logo = """
                 _____                                               \s
                | ____|___ ___  _ __ ___  _ __ ___   ___ _ __ ___ ___\s
                |  _| / __/ _ \\| '_ ` _ \\| '_ ` _ \\ / _ \\ '__/ __/ _ \\
                | |__| (_| (_) | | | | | | | | | | |  __/ | | (_|  __/
                |_____\\___\\___/|_| |_| |_|_| |_| |_|\\___|_|  \\___\\___|
                """;
        System.out.println(logo);
        Product.addProduct(new Product("Shoes 👟👟", 20));
        Product.addProduct(new Product("Wine 🍾", 5));
        Product.addProduct(new Product("Phone 📞", 500.45));
        Product.addProduct(new Product("Laptop 💻", 900));
        Product.addProduct(new Product("Socks 🧦", 2.25));
        Product.addProduct(new Product("Slippers 🥿", 2.67));
        Product.addProduct(new Product("TV 📺", 700));

        Product.viewProducts();
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        signup(scanner);
        Customer customer = login(scanner);
        loggedIn = true;
        System.out.printf("%nOur Product List:%n");
        Product.viewProducts();
        boolean shouldLoop = false;

        do{
            shouldLoop = action(scanner, customer);
            if (!shouldLoop){
                loggedIn = false;
                signup(scanner);
                customer = login(scanner);
                loggedIn = true;
                shouldLoop = true;
                System.out.printf("%nOur Product List:%n");
                Product.viewProducts();
            }
        } while(shouldLoop);


    }

    /**
     * Registers a customer into the ecommerce system.
     * @param scanner {@code Scanner}
     */
    private static void signup(Scanner scanner){

        System.out.println("Hello customer, please enter your username " +
                "to signup and purchase products: ");
        String username = null;
        String name = null;
        while (username == null){
            String dummyUsername = scanner.nextLine();
            if (dummyUsername.length() < 3){
                System.out.println("Invalid username!");
                System.out.println("Username must be more than 2 characters!");
                System.out.println("Enter a valid username: ");
            } else if (Customer.getCustomer(dummyUsername) != null) {
                System.out.println("Username already exists!, do you want to " +
                        "login? (Y?N)");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("y")) return;
                System.out.println("Enter a valid username: ");
            }
            else {
                username = dummyUsername;
            }
        }

        while (name == null){
            System.out.println("Enter your name:");
            String dummyName = scanner.nextLine();

            if (dummyName.length() < 3){
                System.out.println("Invalid Name!");
                System.out.println("Name must be more than 2 characters!");
                System.out.println("Enter a valid name: ");
            }
            else {
                name = dummyName;
            }
        }
        Customer customer = new Customer(username, name);
        Customer.registerCustomer(customer);
    }

    /**
     * logs a customer into the ecommerce system.
     * @param scanner {@code Scanner}
     */
    private static Customer login(Scanner scanner){
        System.out.println("Enter username to login: ");
        String userName = scanner.nextLine();
        Customer customer = Customer.getCustomer(userName);
        if (customer == null){
            System.out.println("Username doesn't exist signup!");
            signup(scanner);
            return login(scanner);
        }
        else {
            System.out.println("You are logged in!");
            System.out.println("Welcome "+ customer.getUsername());
            return customer;
        }
    }

    /**
     * Performs action based on user's role (admin/customer).
     * @param scanner {@code Scanner}
     * @param customer {@code Customer}: a customer in the
     *                                 ecommerce system
     */
    private static boolean action(Scanner scanner, Customer customer){
        System.out.println();
        System.out.println("What would you like to do?");
        if (customer.getUsername().equals("admin")){
            System.out.println("Enter 'l' - logout, 'p' - view profile, 'v' - view products, " +
                    "'c' - view customers, 'o' - view orders, 's' - change order status, 'q' - quit program: ");
        }
        else {
            System.out.println("Enter 'l' - logout, 'p' - view profile, 'v' - view products, " +
                    "'b' - buy products, 's' - view cart, 'c' - checkout, 'q' - quit program: ");
        }
        String cmd = scanner.nextLine();
        System.out.println();

        if (customer.getUsername().equals("admin")){
            return adminAction(customer, cmd);
        }
        else {
            return customerAction(customer, cmd);
        }
    }

    /**
     * Performs administrator action/commands.
     * @param customer The {@code Customer} object representing
     *                 a customer in the e-commerce system.
     * @param cmd {@code String} represents cmd-line input action
     * @return boolean
     */
    private static boolean adminAction(Customer customer, String cmd){
        return switch (cmd){
            case "l":
                yield logout();
            case "v":
                yield viewProducts();
            case "p":
                yield viewProfile(customer);
            case "c":
                yield viewCustomers(customer);
            case "o":
                yield viewOrders(customer);
            case "s":
                yield changeOrderStatus(customer);
            case "q":
                System.out.println("quitting program...");
                System.exit(0);
            default:
                System.out.println("Unknown command");
                yield true;
        };
    }

    /**
     * Performs customer's actions/commands.
     * @param customer The {@code Customer} object representing
     *                 a customer in the e-commerce system.
     * @param cmd {@code String} represents cmd-line input action
     * @return boolean
     */
    private static boolean customerAction(Customer customer, String cmd){
        return switch (cmd){
            case "l":
                yield logout();
            case "v":
                yield viewProducts();
            case "p":
                yield viewProfile(customer);
            case "b":
                yield buyProduct(customer);
            case "s":
                yield viewCart(customer);
            case "c":
                yield checkout(customer);
            case "q":
                //checkout
                System.out.println("quitting program...");
                System.exit(0);
                yield false;
            default:
                System.out.println("Unknown command");
                yield true;
        };

    }

    /**
     * Logs out of ecommerce system.
     * @return false
     */
    private static boolean logout(){
        System.out.println("logging out...");
        return false;
    }

    /**
     * Displays products in ecommerce system.
     * @return true
     */
    private static boolean viewProducts(){
        System.out.printf("%nOur Product List:%n");
        Product.viewProducts();
        return true;
    }

    /**
     * Displays customer's profile.
     * @param customer The {@code Customer} object representing
     *                 a customer in the e-commerce system.
     * @return true
     */
    private static boolean viewProfile(Customer customer){
        System.out.printf("Profile:%n");
        System.out.printf("%-12s %s%n", "ID", customer.getCustomerID());
        System.out.printf("%-12s %s%n", "Username", customer.getUsername());
        System.out.printf("%-12s %s%n", "Name", customer.getName());
        return true;
    }

    /**
     * Adds a requested product to customer's cart.
     * @param customer The {@code Customer} object representing
     *                 a customer in the e-commerce system.
     * @return true
     */
    private static boolean buyProduct(Customer customer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the product you would like " +
                "to add to cart 🛒");
        try {
            int ID = scanner.nextInt();
            if (ID > Product.getProducts().size() || ID <= 0) {
                System.out.println("Product with that ID is not available");
            }
            else {
                customer.addToCart(ID);
                return true;
            }

        } catch (InputMismatchException e){
            System.out.println("Product ID must be a number");
        }
        return true;
    }

    /**
     * Displays customer's shopping cart.
     * @param customer The {@code Customer} object representing
     *                 a customer in the e-commerce system.
     * @return true
     */
    private static boolean viewCart(Customer customer){
        System.out.println("Cart 🛒");
        customer.viewCart();
        return true;
    }

    /**
     * Performs checkout action in an ecommerce system.
     * @param customer The {@code Customer} object representing
     *                 a customer in the e-commerce system.
     * @return true
     */
    private static boolean checkout(Customer customer){
        Scanner scanner = new Scanner(System.in);
        if (customer.getAddress().isBlank()){
            System.out.println("Please enter your address");
            String address = scanner.nextLine();
            if (address.isBlank()) return checkout(customer);
            else {
                customer.setAddress(address);
                customer.placeOrder();
                return true;
            }
        }
        else {
            customer.placeOrder();
            return true;
        }
    }

    /**
     * Admin action - Displays all customers in an ecommerce system.
     * @param customer The {@code Customer} object representing
     *                 a customer in the e-commerce system.
     * @return true
     */
    private static boolean viewCustomers(Customer customer){
        if (customer.getUsername().equals("admin")){
            System.out.printf("%-5s %-15s %-15s %s%n", "ID", "Username",
                    "Name", "Address");
            Customer.getCustomers().forEach(c ->
                System.out.printf("%-5d %-15s %-15s %s%n", c.getCustomerID(),
                        c.getUsername(), c.getName(), c.getAddress())
            );
        }
        return true;
    }

    /**
     * Admin action - Displays all orders in an ecommerce system.
     * @param customer The {@code Customer} object representing
     *                 a customer in the e-commerce system.
     * @return true
     */
    private static boolean viewOrders(Customer customer){
        if (customer.getUsername().equals("admin")){
            System.out.printf("%-10s %-15s %-15s %-35s %-10s %s%n", "OrderID", "Name", "Status", "Products", "Total", "Date");
            Order.getOrderHistory().forEach(o -> {
                String productsString = o.getProducts().stream()
                        .map(p -> p.getQuantity() + " * " +p.getProduct().getName())
                        .reduce("", (acc, p) -> acc + p + ", ");

                System.out.printf("%-10d %-15s %-15s %-35s $%-10.2f %s%n",
                        o.getOrderID(), o.getCustomer().getName(), o.getOrderStatus(),
                        productsString, o.getTotal(), o.getDate());
            });
        }
        return true;
    }

    /**
     * Admin action - Changes an order status in an ecommerce system.
     * @param customer The {@code Customer} object representing
     *                 a customer in the e-commerce system.
     * @return true
     */
    private static boolean changeOrderStatus(Customer customer){
        Scanner scanner = new Scanner(System.in);
        if (!customer.getUsername().equals("admin")) {
            System.out.println("Only admin can make such change");
            return true;
        }
        System.out.println("Enter the ID of the order you would like " +
                "to change the status of:");
        try {
            int ID =  Integer.parseInt(scanner.nextLine());
            if (ID > Order.getOrderHistory().size() || ID <= 0) {
                System.out.println("Order with that ID is not available");
                return true;
            }
            Order order = Order.getOrderHistory().get(ID - 1);
            System.out.println("Enter the status of the order:");
            String status = scanner.nextLine();
            order.setOrderStatus(status);
        } catch (InputMismatchException e){
            System.out.println("Product ID must be a number");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }
}