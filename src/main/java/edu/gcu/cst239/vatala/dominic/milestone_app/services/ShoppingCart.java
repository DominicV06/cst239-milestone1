package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import java.util.ArrayList;
import java.util.List;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.CartItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.InventoryItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

/**
 * Implements the shared customer shopping cart.
 * The cart validates requested quantities against the
 * shared inventory service.
 *
 * @author Dominic Vatala
 * @version 2.0
 */
public class ShoppingCart implements CartService {

    private final List<CartItem> cartItems;
    private final InventoryService inventoryService;

    /**
     * Creates an empty shopping cart using the shared inventory service.
     *
     * @param inventoryService the shared inventory service
     */
    public ShoppingCart(InventoryService inventoryService) {

        if (inventoryService == null) {
            throw new IllegalArgumentException(
                    "Inventory service cannot be null.");
        }

        this.inventoryService = inventoryService;
        cartItems = new ArrayList<>();
    }

    /**
     * Returns a read-only copy of the current cart.
     *
     * @return the current cart items
     */
    @Override
    public List<CartItem> getAllCartItems() {
        return List.copyOf(cartItems);
    }

    /**
     * Adds a requested quantity of a product to the cart
     * when sufficient inventory is available.
     *
     * @param product the product to add
     * @param quantity the requested quantity
     * @return true when the cart changes
     */
    @Override
    public boolean addProduct(Product product, int quantity) {

        if (product == null || quantity <= 0) {
            return false;
        }

        InventoryItem inventoryItem =
                inventoryService.getInventoryItemByProductId(
                        product.getId());

        if (inventoryItem == null) {
            return false;
        }

        CartItem existingCartItem =
                getCartItemByProductId(product.getId());

        int existingQuantity =
                existingCartItem == null
                        ? 0
                        : existingCartItem.getQuantity();

        int requestedTotal =
                existingQuantity + quantity;

        if (requestedTotal
                > inventoryItem.getQuantityInStock()) {

            return false;
        }

        if (existingCartItem != null) {

            existingCartItem.setQuantity(
                    requestedTotal);

            return true;
        }

        cartItems.add(
                new CartItem(product, quantity));

        return true;
    }

    /**
     * Changes the quantity of an existing cart item.
     * A quantity of zero removes the item from the cart.
     *
     * @param productId the product identifier
     * @param quantity the requested quantity
     * @return true when the cart changes
     */
    @Override
    public boolean updateQuantity(
            int productId,
            int quantity) {

        if (quantity < 0) {
            return false;
        }

        CartItem cartItem =
                getCartItemByProductId(productId);

        if (cartItem == null) {
            return false;
        }

        if (quantity == 0) {
            return removeProductFromCart(productId);
        }

        InventoryItem inventoryItem =
                inventoryService.getInventoryItemByProductId(
                        productId);

        if (inventoryItem == null) {
            return false;
        }

        if (quantity
                > inventoryItem.getQuantityInStock()) {

            return false;
        }

        cartItem.setQuantity(quantity);

        return true;
    }

    /**
     * Removes a product from the cart using its product identifier.
     *
     * @param productId the product identifier
     * @return true when the item is removed
     */
    @Override
    public boolean removeProductFromCart(int productId) {

        CartItem cartItem =
                getCartItemByProductId(productId);

        if (cartItem == null) {
            return false;
        }

        return cartItems.remove(cartItem);
    }

    /**
     * Calculates the current total cost of the cart.
     *
     * @return the total price of all cart items
     */
    @Override
    public double getCartTotal() {

        double total = 0.0;

        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }

        return total;
    }

    /**
     * Completes the purchase when all requested quantities
     * are still available in inventory.
     *
     * @return true when checkout succeeds
     */
    @Override
    public boolean checkout() {

        if (cartItems.isEmpty()) {
            return false;
        }

        for (CartItem cartItem : cartItems) {

            InventoryItem inventoryItem =
                    inventoryService
                            .getInventoryItemByProductId(
                                    cartItem.getProduct().getId());

            if (inventoryItem == null
                    || inventoryItem.getQuantityInStock()
                    < cartItem.getQuantity()) {

                return false;
            }
        }

        for (CartItem cartItem : cartItems) {

            InventoryItem inventoryItem =
                    inventoryService
                            .getInventoryItemByProductId(
                                    cartItem.getProduct().getId());

            int newQuantity =
                    inventoryItem.getQuantityInStock()
                            - cartItem.getQuantity();

            inventoryService.updateQuantity(
                    cartItem.getProduct().getId(),
                    newQuantity);
        }

        clearCart();

        return true;
    }

    /**
     * Removes every item from the cart.
     */
    @Override
    public void clearCart() {
        cartItems.clear();
    }

    /**
     * Finds a cart item using a product identifier.
     *
     * @param productId the product identifier
     * @return the matching cart item, or null if not found
     */
    private CartItem getCartItemByProductId(int productId) {

        for (CartItem item : cartItems) {

            if (item.getProduct().getId() == productId) {
                return item;
            }
        }

        return null;
    }
}

