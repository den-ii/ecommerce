package com.ecommerce.orders;

import com.ecommerce.Product;


/**
 * The CartItem Class represent a cart item in an ecommerce system.
 * a POJO.
 * It contains information about a cart item such as:
 * product, price, quantity, price.
 *
 * @author Deni Wisdom Ochiche
 */
public class CartItem{
    private int quantity;
    private double price;
    private final Product product;

    /**
     * CartItem constructor.
     * @param product {@code Product}
     */
    public CartItem(Product product){
        this.product = product;
        this.price = product.getPrice();
        quantity++;
    }

    /**
     * Retrieves the product quantity
     * @return int: quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Retrieves the product
     * @return {@code Product}
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the quantity of the product.
     * @param quantity int: quantity of product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.price = product.getPrice() * this.quantity;
    }

    /**
     * Retrieves price of {@code CartItem}
     * @return double: price
     */
    public double getPrice() {
        return price;
    }

}
