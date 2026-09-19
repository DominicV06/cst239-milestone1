package edu.gcu.cst239.vatala.dominic.milestone_app.models;

import java.util.Objects;

/**
 * Represents a product and the quantity currently available in inventory.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class InventoryItem {

    private final Product product;
    private int quantityInStock;

    /**
     * Creates an inventory item for a product.
     *
     * @param product the product stored in inventory
     * @param quantityInStock the starting inventory quantity
     * @throws NullPointerException if the product is null
     * @throws IllegalArgumentException if the quantity is negative
     */
    public InventoryItem(Product product, int quantityInStock) {
        this.product = Objects.requireNonNull(
                product,
                "Product is required.");

        if (quantityInStock < 0) {
            throw new IllegalArgumentException(
                    "Quantity cannot be negative.");
        }

        this.quantityInStock = quantityInStock;
    }

    /**
     * Returns the product associated with this inventory item.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Returns the current quantity in stock.
     *
     * @return the quantity in stock
     */
    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * Increases the inventory quantity by a positive amount.
     *
     * @param amount the amount to add
     * @throws IllegalArgumentException if the amount is zero or negative
     */
    public void increaseQuantity(int amount) {
        requirePositiveAmount(amount);
        quantityInStock += amount;
    }

    /**
     * Decreases the inventory quantity by a positive amount.
     *
     * @param amount the amount to remove
     * @return true if the quantity was decreased, or false if there was not enough stock
     * @throws IllegalArgumentException if the amount is zero or negative
     */
    public boolean decreaseQuantity(int amount) {
        requirePositiveAmount(amount);

        if (amount > quantityInStock) {
            return false;
        }

        quantityInStock -= amount;
        return true;
    }

    /**
     * Verifies that a quantity-change amount is greater than zero.
     *
     * @param amount the amount being checked
     * @throws IllegalArgumentException if the amount is zero or negative
     */
    private void requirePositiveAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero.");
        }
    }

    /**
     * Returns a readable representation of this inventory item.
     *
     * @return the product and its inventory quantity
     */
    @Override
    public String toString() {
        return product +
                ", quantityInStock=" + quantityInStock;
    }
}