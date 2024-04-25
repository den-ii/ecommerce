package com.ecommerce.orders;

import com.ecommerce.Customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Order class represents an Order in an ecommerce system.
 * It contains information about a customer's order.
 *
 * @author Deni Wisdom Ochiche
 */
public class Order {

    /*
     * Contains only valid order status's
     */
    public enum Status { PENDING, DELIVERED, CANCELLED}

    /*
     * Stores all orders in an ecommerce
     * system.
     */
    private static final ArrayList<Order> orderHistory = new ArrayList<>();

    /*
     * Length of order for OrderId allocation.
     */
    private static int length = 0;
    private final int orderId;
    private final Customer customer;
    private final List<CartItem> products;
    private final double total;
    private Status orderStatus;
    private final Date date;


    /**
     * Order constructor.
     * @param customer {@code Customer}: a customer.
     * @param products {@code List(Customer)}: a customer.
     * @param total int: total amount.
     */
    public Order(Customer customer, List<CartItem> products, double total){
        this.orderId = ++length;
        this.products = products;
        this.customer = customer;
        this.total = total;
        this.orderStatus = Status.PENDING;
        this.date = new Date();
    }

    /**
     * Retrieves orderID
     * @return int: an order's id.
     */
    public int getOrderID() {
        return orderId;
    }

    /**
     * Retrieves {@code Customer} related to {@code Order}.
     * @return {@code Customer}.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Retrieves products in customer's cart.
     * @return {@code List(CartItem)}
     */
    public List<CartItem> getProducts() {
        return products;
    }

    /**
     * Adds a list of products to order's product list
     * @param p {@code List<CartItem>}
     */
    public void addProducts(List<CartItem> p){
        products.addAll(p);
    }

    /**
     * Retrieves total sum of product's amount in order
     * @return int: total
     */
    public double getTotal() {
        return total;
    }

    /**
     * Retrieves the date issued when ordered.
     * @return {@code Date}: date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Retrieves Order history for admin.
     * @return {@code List<Order>}: order history.
     */
    public static List<Order> getOrderHistory() {
        return List.copyOf(orderHistory);
    }

    /**
     *  Adds an order to order history.
     * @param order {@code Order}
     */
    public static void addOrderToHistory(Order order) {
        orderHistory.add(order);
    }

    /**
     * Retrieves the status of an order.
     * @return {@code Status}
     */
    public Status getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the status of an order.
     * @param orderStatus {@code Status}
     */
    public void setOrderStatus(String orderStatus) {
        try {
            this.orderStatus = Status.valueOf(orderStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: "
                    + orderStatus);
        }
    }

    /**
     * Summarize and displays order.
     */
    public void orderSummary(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        System.out.printf("%-7s %d%n","OrderID:", orderId);
        System.out.printf("%-7s %s%n", "Customer name:",customer.getName());
        System.out.println();
        System.out.printf("%-5s %-15s %-10s %s%n", "ID", "Product", "Qty", "Price");
        products.forEach(p -> {
            System.out.printf("%-5d %-15s %-10d %.2f%n", p.getProduct().getProductID(),
                    p.getProduct().getName(), p.getQuantity(), p.getPrice());
        });
        System.out.println();
        System.out.printf("%-7s %s%n", "Order Status:", orderStatus);
        System.out.printf("%-10s %s%n", "Date:", formattedDate);
    }

}
