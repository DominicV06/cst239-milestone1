package edu.gcu.cst239.vatala.dominic.milestone_app.actions;

import edu.gcu.cst239.vatala.dominic.milestone_app.services.StoreFront;
import edu.gcu.cst239.vatala.dominic.milestone_app.util.InputUtilities;

/**
 * Provides the menu and placeholder actions available to customers.
 * Customer shopping functionality will be implemented in later milestones.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class CustomerActions {

    private final StoreFront store;

    /**
     * Creates the customer actions controller.
     *
     * @param store the shared storefront application
     */
    public CustomerActions(StoreFront store) {
        this.store = store;
    }

    /**
     * Displays the customer menu and processes customer selections.
     * Milestone 1 customer operations remain placeholders.
     */
    public void handleCustomerActions() {
        InputUtilities input = new InputUtilities();
        boolean exitRequested = false;

        while (!exitRequested) {
            System.out.println();
            System.out.println("Customer Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Search for product by name or description");
            System.out.println("3. Add Product to Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Checkout");
            System.out.println("0. Exit");

            int choice = input.readInt("Choose an option:", 0, 6);

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
                    System.out.println("You chose to add a product to the cart.");
                    showPlaceholder();
                    break;

                case 4:
                    System.out.println("You chose to remove a product from the cart.");
                    showPlaceholder();
                    break;

                case 5:
                    System.out.println("You chose to view the cart.");
                    showPlaceholder();
                    break;

                case 6:
                    System.out.println("You chose to checkout.");
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