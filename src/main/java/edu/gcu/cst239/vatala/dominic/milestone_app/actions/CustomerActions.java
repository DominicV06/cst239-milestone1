package edu.gcu.cst239.vatala.dominic.milestone_app.actions;

import java.util.List;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.InventoryItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;
import edu.gcu.cst239.vatala.dominic.milestone_app.services.InventoryService;
import edu.gcu.cst239.vatala.dominic.milestone_app.services.StoreFront;
import edu.gcu.cst239.vatala.dominic.milestone_app.util.InputUtilities;

/**
 * Provides the menu and actions available to customers.
 * Customer inventory operations are delegated to the shared
 * InventoryService.
 *
 * @author Dominic Vatala
 * @version 2.0
 */
public class CustomerActions {

    private final InventoryService inventoryService;
    private final InputUtilities input;

    /**
     * Creates the customer actions controller.
     *
     * @param store the shared storefront application
     */
    public CustomerActions(StoreFront store) {
        inventoryService = store.getInventoryManager();
        input = new InputUtilities();
    }

    /**
     * Displays the customer menu and processes customer selections.
     */
    public void handleCustomerActions() {

        boolean exitRequested = false;

        while (!exitRequested) {

            System.out.println();
            System.out.println("Customer Menu:");
            System.out.println("1. View Products");
            System.out.println(
                    "2. Search for product by name or description");
            System.out.println("3. Add Product to Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Checkout");
            System.out.println("0. Exit");

            int choice =
                    input.readInt(
                            "Choose an option:",
                            0,
                            6);

            switch (choice) {

                case 1:
                    viewProducts();
                    break;

                case 2:
                    searchProducts();
                    break;
      

                case 3:
                    System.out.println(
                            "You chose to add a product to the cart.");
                    showPlaceholder();
                    break;

                case 4:
                    System.out.println(
                            "You chose to remove a product from the cart.");
                    showPlaceholder();
                    break;

                case 5:
                    System.out.println(
                            "You chose to view the cart.");
                    showPlaceholder();
                    break;

                case 6:
                    System.out.println(
                            "You chose to checkout.");
                    showPlaceholder();
                    break;

                case 0:
                    exitRequested = true;
                    break;

                default:
                    System.out.println(
                            "Invalid selection. Please try again.");
                    break;
            }
        }
    }

    /**
     * Allows the customer to view available products
     * using a selected sort order.
     */
    private void viewProducts() {

        System.out.println();
        System.out.println("View Products:");
        System.out.println("1. Sort by name");
        System.out.println("2. Sort by manufacture date");
        System.out.println("3. Sort by price");

        int sortChoice =
                input.readInt(
                        "Choose a sort option:",
                        1,
                        3);

        List<InventoryItem> inventory;

        switch (sortChoice) {

            case 1:
                inventory =
                        inventoryService
                                .getInventoryItemsSortedByName();
                break;

            case 2:
                inventory =
                        inventoryService
                                .getInventoryItemsSortedByDate();
                break;

            case 3:
                inventory =
                        inventoryService
                                .getInventoryItemsSortedByPrice();
                break;

            default:
                return;
        }

        System.out.println();
        System.out.println("Available Products:");
        System.out.println("-------------------");

        boolean productDisplayed = false;

        for (InventoryItem item : inventory) {

            if (item.getQuantityInStock() > 0) {
                displayInventoryItem(item);
                productDisplayed = true;
            }
        }

        if (!productDisplayed) {
            System.out.println(
                    "There are currently no products available.");
        }
    }
    /**
 * Searches available products by name or description.
 */
private void searchProducts() {

    System.out.println();
    System.out.println("Search Products:");
    System.out.println("1. Search by product name");
    System.out.println("2. Search by product description");

    int choice =
            input.readInt(
                    "Choose a search option:",
                    1,
                    2);

    String searchTerm =
            input.readString(
                    "Enter search text:");

    List<InventoryItem> matches;

    if (choice == 1) {
        matches =
                inventoryService
                        .searchProductsByName(searchTerm);
    } else {
        matches =
                inventoryService
                        .searchProductsByDescription(searchTerm);
    }

    if (matches.isEmpty()) {
        System.out.println(
                "No matching products were found.");
        return;
    }

    System.out.println();
    System.out.println("Search Results:");
    System.out.println("---------------");

    for (InventoryItem item : matches) {

        Product product = item.getProduct();

        System.out.println(
                "ID: " + product.getId());

        System.out.println(
                "Name: " + product.getName());

        System.out.println(
                "Description: "
                        + product.getDescription());

        if (item.getQuantityInStock() > 0) {
            System.out.println("Availability: In Stock");
        } else {
            System.out.println("Availability: Out of Stock");
        }

        System.out.println();
    }
}


    /**
     * Displays one product that is available for purchase.
     *
     * @param item the inventory item to display
     */
    private void displayInventoryItem(InventoryItem item) {

        Product product = item.getProduct();

        System.out.println(
                "ID: " + product.getId());

        System.out.println(
                "Name: " + product.getName());

        System.out.println(
                "Description: "
                        + product.getDescription());

        System.out.println(
                "Manufacture Date: "
                        + product.getDateOfManufacture());

        System.out.printf(
                "Price: $%.2f%n",
                product.getPrice());

        System.out.println(
                "Category: "
                        + product.getCategory());

        System.out.println(
                "Quantity Available: "
                        + item.getQuantityInStock());

        System.out.println();
    }

    /**
     * Displays a placeholder message for customer features
     * that will be completed in later Milestone 2 branches.
     */
    private void showPlaceholder() {
        System.out.println(
                "This feature will be completed "
                        + "in a later Milestone 2 branch.");
    }
}
