package edu.gcu.cst239.vatala.dominic.milestone_app.actions;

import edu.gcu.cst239.vatala.dominic.milestone_app.services.InventoryService;
import edu.gcu.cst239.vatala.dominic.milestone_app.services.StoreFront;
import edu.gcu.cst239.vatala.dominic.milestone_app.util.InputUtilities;

/**
 * Provides the menu and actions available to a store manager.
 * Inventory operations are placeholders during the application-frame branch.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class StoreManagerActions {

    private final StoreFront store;
    private final InventoryService inventoryService;

    /**
     * Creates the store-manager actions controller.
     *
     * @param store the shared storefront application
     */
    public StoreManagerActions(StoreFront store) {
        this.store = store;
        this.inventoryService = store.getInventoryManager();
    }

    /**
     * Displays the store-manager menu and processes manager selections.
     * Inventory operations are implemented in later branches.
     */
    public void handleManagerActions() {
        InputUtilities input = new InputUtilities();
        boolean exitRequested = false;

        while (!exitRequested) {
            System.out.println();
            System.out.println("Store Manager Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Search for a product by name or description");
            System.out.println("3. Add Product to Inventory");
            System.out.println("4. Remove Product from Inventory");
            System.out.println("5. Update Product in Inventory");
            System.out.println("6. Save Inventory to File");
            System.out.println("7. Load Inventory from File");
            System.out.println("0. Exit");

            int choice = input.readInt("Choose an option:", 0, 7);

            switch (choice) {
                case 1:
                    System.out.println("You chose to view products.");
                    showPlaceholder();
                    break;

                case 2:
                    System.out.println(
                            "You chose to search for a product by name or description.");
                    showPlaceholder();
                    break;

                case 3:
                    System.out.println("You chose to add a product to inventory.");
                    showPlaceholder();
                    break;

                case 4:
                    System.out.println("You chose to remove a product from inventory.");
                    showPlaceholder();
                    break;

                case 5:
                    System.out.println("You chose to update a product in inventory.");
                    showPlaceholder();
                    break;

                case 6:
                    System.out.println("You chose to save inventory to a file.");
                    showPlaceholder();
                    break;

                case 7:
                    System.out.println("You chose to load inventory from a file.");
                    showPlaceholder();
                    break;

                case 0:
                    exitRequested = true;
                    break;

                default:
                    System.out.println("Invalid selection.");
                    break;
            }
        }
    }

    /**
     * Displays the Milestone 1 placeholder message.
     */
    private void showPlaceholder() {
        System.out.println(
                "This is milestone #1. No actual functionality has been implemented yet.");
    }
}