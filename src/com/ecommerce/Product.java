package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

/**
 * The Product class represents a product in an ecommerce
 * system.
 * It stores information about products such as their name,
 * price, and quantity.
 *
 * @author Deni Wisdom Ochiche
 */
public class Product {

    /*
     * Stores all products in an ecommerce
     * system.
     */
    private static final ArrayList<Product> products = new ArrayList<>();

    /*
     * Keeps track of length for
     * product ID allocation.
     */
    private static int length = 0;
    private final int productID;
    private String name;
    private double price;

    /**
     * Product class constructor.
     * @param name product's name.
     * @param price product's price.
     */
    public Product(String name, double price){
        this.productID = ++length;
        this.name = name;
        this.price = price;
    }

    /**
     * Retrieves a product's id.
     * @return int product's id.
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Retrieves a product's name.
     * @return String product's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves a product's price.
     * @return String product's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Registers a product
     */
    public static void addProduct(Product p){
        products.add(p);
    }

    /**
     * Returns the list of products registered.
     * @return List(Product).
     */
    public static List<Product> getProducts() {
        return List.copyOf(products);
    }

    /**
     * Displays all registered products.
     */
    public static void viewProducts(){
        System.out.printf("%-15s %-15s %s%n", "ID", "Product", "Price");
        products.forEach(p -> System.out.printf("%-15d %-15s $%.2f%n"
                , p.getProductID(), p.getName(), p.getPrice()));
    }
}
