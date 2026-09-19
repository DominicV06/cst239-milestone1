package edu.gcu.cst239.vatala.dominic.milestone_app.actions;

import edu.gcu.cst239.vatala.dominic.milestone_app.services.StoreFront;
import edu.gcu.cst239.vatala.dominic.milestone_app.util.InputUtilities;

/**
 * Provides the initial role-selection menu for the storefront.
 * Users may enter the customer menu, store-manager menu, or exit.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class StartingActions {

    private final StoreFront store;

    /**
     * Creates the starting-actions controller.
     *
     * @param store the shared storefront application
     */
    public StartingActions(StoreFront store) {
        this.store = store;
    }

    /**
     * Starts the storefront role-selection workflow.
     */
    public void start() {
        InputUtilities input = new InputUtilities();
        boolean exitRequested = false;

        while (!exitRequested) {
            System.out.println();
            System.out.println("Welcome to the StoreFront application!");
            System.out.println("How would you like to use the app?");
            System.out.println("1. Customer");
            System.out.println("2. Store manager");
            System.out.println("3. Exit");

            int choice = input.readInt(
                    "Enter 1, 2 or 3:",
                    1,
                    3);

            switch (choice) {
                case 1:
                    System.out.println(
                            "You are using the app as a customer.");

                    CustomerActions customerActions =
                            new CustomerActions(store);

                    customerActions.handleCustomerActions();
                    break;

                case 2:
                    System.out.println(
                            "You are using the app as a store manager.");

                    StoreManagerActions managerActions =
                            new StoreManagerActions(store);

                    managerActions.handleManagerActions();
                    break;

                case 3:
                    exitRequested = true;
                    break;

                default:
                    System.out.println(
                            "Invalid selection. Please try again.");
                    break;
            }
        }
    }
}