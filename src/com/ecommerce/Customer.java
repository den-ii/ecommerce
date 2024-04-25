package com.ecommerce;

import com.ecommerce.orders.CartItem;
import com.ecommerce.orders.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class represents a customer entity in an ecommerce
 * system.
 * It contains information about customers such as their name,
 * contact details, and membership status.
 * It also performs actions such as purchasing a product.
 *
 * @author Deni Wisdom Ochiche
 */
public class Customer {

    /*
     * Stores all customers in an ecommerce
     * system.
     */
    private static final ArrayList<Customer> customers = new ArrayList<>();

    /*
     * Keeps track of customers for ID allocation.
     */
    private static int length = 0;
    private final int customerID;
    private String name;
    private String address = "";
    private String username;

    /*
     * Contains list of products added to customer's cart
     * in an ecommerce system.
     */
    private ArrayList<CartItem> shoppingCart = new ArrayList<>();

    /**
     * Customer constructor
     * @param username String:customer's username.
     * @param name String:customer's name.
     */
    public Customer(String username, String name){
        this.customerID = ++length;
        this.name = name;
        this.username = username;
    }

    /**
     * Retrieves Customer's ID.
     * @return int ID of a customer.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Retrieves Customer's Name.
     * @return String name of a customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves Customer's username.
     * @return String username of a customer.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves Customer's address.
     * @return String address of a customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Retrieves List of Customers.
     * @return List(Customer) List of Customers.
     */
    public static List<Customer> getCustomers() {
        return List.copyOf(customers);
    }

    /**
     * Modifies customer's address.
     * @param address String: new address of a customer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Registers customer.
     * @param customer {@code Customer}: a new customer.
     */
    public static void registerCustomer(Customer customer){
        customers.add(customer);
    }

    /**
     * Retrieves items in customer's cart.
     * @return {@code List(CartItem)} customer's cart.
     */
    public List<CartItem> getShoppingCart() {
        return List.copyOf(shoppingCart);
    }

    /**
     * Retrieves a customer.
     * @param username String: username of customer.
     * @return {@code Customer} if found, else null.
     */
    public static Customer getCustomer(String username){
        for (var customer: getCustomers()){
            if (customer.getUsername().equals(username)){
                return customer;
            }
        }
        return null;
    }

    /**
     * Changes a customer's name.
     * @param name String: new name of customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a product to customer's shopping cart.
     * @param ID int: the product's ID.
     */
    public void addToCart(int ID){
        Product product = Product.getProducts().get(ID - 1);
        for (var c: shoppingCart){
            if (c.getProduct().getProductID() == product
                    .getProductID()){
                int quantity = c.getQuantity();
                c.setQuantity(++quantity);
                System.out.println("Product added successfully");
                System.out.println();
                viewCart();
                return;
            }
        }
        CartItem cartItem = new CartItem(product);
        shoppingCart.add(cartItem);
        System.out.println("Product added successfully");
        System.out.println();
        viewCart();
    }

    /**
     * Removes a product from customer's shopping cart.
     * @param ID int: the product's ID.
     */
    public void removeFromCart(int ID){
        shoppingCart.removeIf(c -> c.getProduct().getProductID() == ID);
        System.out.println("Product removed successfully");

    }

    /**
     * Calculates the total amount to be paid by the customer.
     * @return total double: sum of product prices in shopping cart.
     */
    public double calculateTotal(){
        return shoppingCart.stream()
                .mapToDouble(CartItem::getPrice).sum();
    }

    /**
     * Displays products in customer's shopping cart.
     */
    public void viewCart(){
        if (shoppingCart.isEmpty()){
            System.out.println("Cart is Empty!");
            return;
        }
        System.out.printf("%-5s %-15s %-10s %s%n", "ID",
                "Product", "Qty", "Price");
        shoppingCart.forEach(c ->
            System.out.printf("%-5d %-15s %-10d %.2f%n", c.getProduct()
                            .getProductID(), c.getProduct().getName(),
                    c.getQuantity(), c.getPrice())

        );

        System.out.printf("%-10s %-5.2f%n", "Total Price", calculateTotal());
    }

    /**
     * Places a customer's order, charge customer, clears cart.
     */
    public void placeOrder(){
        if (shoppingCart.isEmpty()){
            System.out.println("Cart🛒 is Empty!");
            return;
        }
        double total = calculateTotal();
        Order order = new Order(this, getShoppingCart(), total);
        Order.addOrderToHistory(order);
        order.orderSummary();
        System.out.println("You've been charged $" + total);
        shoppingCart = new ArrayList<>();
        System.out.println("Cart Cleared!");
    }

}
