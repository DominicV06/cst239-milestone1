package edu.gcu.cst239.vatala.dominic.milestone_app.models;

/**
 * Represents one product and the quantity a customer
 * intends to purchase.
 *
 * CartItem is separate from InventoryItem because cart
 * quantity and store inventory quantity are different values.
 *
 * @author Dominic Vatala
 * @version 2.0
 */
public class CartItem {

    private final Product product;
    private int quantity;

    /**
     * Creates a cart item.
     *
     * @param product the product being purchased
     * @param quantity the requested quantity
     */
    public CartItem(Product product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException(
                    "Product cannot be null.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Cart quantity must be greater than zero.");
        }

        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Returns the product associated with this cart item.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Returns the quantity currently in the cart.
     *
     * @return the cart quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Changes the quantity stored in this cart item.
     *
     * @param quantity the new positive quantity
     */
    public void setQuantity(int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Cart quantity must be greater than zero.");
        }

        this.quantity = quantity;
    }

    /**
     * Returns the subtotal for this cart item.
     *
     * @return product price multiplied by cart quantity
     */
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}

