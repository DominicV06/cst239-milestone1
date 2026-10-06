package edu.gcu.cst239.vatala.dominic.milestone_app.services;

/**
 * Represents the storefront application and provides access
 * to the application's shared services.
 *
 * StoreFront owns one shared inventory service and one shared
 * cart service. Inventory and cart operations are delegated
 * through their service interfaces.
 *
 * @author Dominic Vatala
 * @version 2.0
 */
public class StoreFront {

    private final InventoryService inventoryManager;
    private final CartService cartService;

    /**
     * Creates the storefront and its required shared services.
     */
    public StoreFront() {

        inventoryManager = new InventoryManager();

        cartService =
                new ShoppingCart(inventoryManager);
    }

    /**
     * Returns the service responsible for managing inventory.
     *
     * @return the shared inventory service
     */
    public InventoryService getInventoryManager() {
        return inventoryManager;
    }

    /**
     * Returns the shared customer shopping cart service.
     *
     * @return the shared cart service
     */
    public CartService getCartService() {
        return cartService;
    }
}
