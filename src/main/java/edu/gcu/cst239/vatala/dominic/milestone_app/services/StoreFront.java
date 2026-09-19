package edu.gcu.cst239.vatala.dominic.milestone_app.services;

/**
 * Represents the storefront application and provides access
 * to the application's shared services.
 *
 * StoreFront does not directly manage inventory. Inventory
 * operations are delegated to InventoryManager through the
 * InventoryService interface.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class StoreFront {

    private final InventoryService inventoryManager;

    /**
     * Creates the storefront and its required inventory service.
     */
    public StoreFront() {
        inventoryManager = new InventoryManager();
    }

    /**
     * Returns the service responsible for managing inventory.
     *
     * @return the shared inventory service
     */
    public InventoryService getInventoryManager() {
        return inventoryManager;
    }
}