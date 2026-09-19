package edu.gcu.cst239.vatala.dominic.milestone_app.actions;

import java.time.LocalDate;
import java.util.List;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.InventoryItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;
import edu.gcu.cst239.vatala.dominic.milestone_app.services.InventoryService;
import edu.gcu.cst239.vatala.dominic.milestone_app.services.StoreFront;
import edu.gcu.cst239.vatala.dominic.milestone_app.util.InputUtilities;

/**
 * Provides inventory-management actions for store managers.
 * Inventory operations are delegated to the shared InventoryService.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class StoreManagerActions {

    private final InventoryService inventoryService;
    private final InputUtilities input;

    /**
     * Creates the store-manager actions controller.
     *
     * @param store the shared storefront application
     */
    public StoreManagerActions(StoreFront store) {
        inventoryService = store.getInventoryManager();
        input = new InputUtilities();
    }

    /**
     * Displays the store-manager menu and processes manager selections.
     */
    public void handleManagerActions() {
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

            int choice = input.readInt(
                    "Choose an option:",
                    0,
                    7);

            switch (choice) {
                case 1:
                    displayInventory();
                    break;

                case 2:
                    searchInventory();
                    break;

                case 3:
                    addProduct();
                    break;

                case 4:
                    removeProduct();
                    break;

                case 5:
                    updateProduct();
                    break;

                case 6:
                    System.out.println(
                            "Save inventory is not implemented in Milestone 1.");
                    break;

                case 7:
                    System.out.println(
                            "Load inventory is not implemented in Milestone 1.");
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
     * Displays every product currently in inventory.
     */
    private void displayInventory() {
        List<InventoryItem> inventory =
                inventoryService.getAllInventoryItems();

        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println();
        System.out.println("Current Inventory:");
        System.out.println("------------------");

        for (InventoryItem item : inventory) {
            displayInventoryItem(item);
        }
    }

    /**
     * Searches inventory using a product name or description.
     */
    private void searchInventory() {
        System.out.println();
        System.out.println("Search Inventory:");
        System.out.println("1. Search by product name");
        System.out.println("2. Search by product description");

        int choice = input.readInt(
                "Choose a search option:",
                1,
                2);

        String searchTerm =
                input.readString("Enter search text:");

        List<InventoryItem> matches;

        if (choice == 1) {
            matches =
                    inventoryService.searchProductsByName(
                            searchTerm);
        } else {
            matches =
                    inventoryService.searchProductsByDescription(
                            searchTerm);
        }

        if (matches.isEmpty()) {
            System.out.println("No matching products were found.");
            return;
        }

        System.out.println();
        System.out.println("Search Results:");
        System.out.println("---------------");

        for (InventoryItem item : matches) {
            displayInventoryItem(item);
        }
    }

    /**
     * Reads product information and adds a new inventory item.
     */
    private void addProduct() {
        System.out.println();
        System.out.println("Add Product:");

        int id =
                input.readInt("Enter product ID:");

        if (inventoryService.getInventoryItemByProductId(id)
                != null) {

            System.out.println(
                    "A product with that ID already exists.");
            return;
        }

        String name =
                input.readString("Enter product name:");

        String description =
                input.readString("Enter product description:");

        LocalDate manufactureDate =
                input.readLocalDate(
                        "Enter manufacture date",
                        "yyyy-MM-dd");

        double price =
                input.readDouble(
                        "Enter product price:",
                        0.0,
                        Double.MAX_VALUE);

        String category =
                input.readString("Enter product category:");

        int quantity =
                input.readInt(
                        "Enter starting quantity:",
                        0,
                        Integer.MAX_VALUE);

        try {
            Product product = Product.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .dateOfManufacture(manufactureDate)
                    .price(price)
                    .category(category)
                    .build();

            InventoryItem item =
                    new InventoryItem(
                            product,
                            quantity);

            boolean added =
                    inventoryService.addInventoryItem(item);

            if (added) {
                System.out.println(
                        "Product added successfully.");
            } else {
                System.out.println(
                        "Product could not be added.");
            }

        } catch (IllegalArgumentException
                | IllegalStateException e) {

            System.out.println(
                    "Product could not be added: "
                            + e.getMessage());
        }
    }

    /**
     * Removes an inventory item using its product identifier.
     */
    private void removeProduct() {
        int productId =
                input.readInt(
                        "Enter the product ID to remove:");

        InventoryItem item =
                inventoryService.getInventoryItemByProductId(
                        productId);

        if (item == null) {
            System.out.println(
                    "No product was found with that ID.");
            return;
        }

        boolean removed =
                inventoryService.removeProductById(productId);

        if (removed) {
            System.out.println(
                    "Product removed successfully.");
        } else {
            System.out.println(
                    "Product could not be removed.");
        }
    }

    /**
     * Allows the manager to update product information
     * or inventory quantity.
     */
    private void updateProduct() {
        int productId =
                input.readInt(
                        "Enter the product ID to update:");

        InventoryItem existingItem =
                inventoryService.getInventoryItemByProductId(
                        productId);

        if (existingItem == null) {
            System.out.println(
                    "No product was found with that ID.");
            return;
        }

        System.out.println();
        System.out.println("Update Options:");
        System.out.println("1. Update product information");
        System.out.println("2. Update inventory quantity");

        int choice =
                input.readInt(
                        "Choose an update option:",
                        1,
                        2);

        if (choice == 1) {
            updateProductInformation(
                    productId,
                    existingItem);
        } else {
            updateProductQuantity(productId);
        }
    }

    /**
     * Updates the product information while preserving quantity.
     *
     * @param productId the product identifier
     * @param existingItem the current inventory item
     */
    private void updateProductInformation(
            int productId,
            InventoryItem existingItem) {

        Product currentProduct =
                existingItem.getProduct();

        System.out.println();
        System.out.println(
                "Current product: "
                        + currentProduct.getName());

        String name =
                input.readString("Enter new product name:");

        String description =
                input.readString(
                        "Enter new product description:");

        LocalDate manufactureDate =
                input.readLocalDate(
                        "Enter new manufacture date",
                        "yyyy-MM-dd");

        double price =
                input.readDouble(
                        "Enter new product price:",
                        0.0,
                        Double.MAX_VALUE);

        String category =
                input.readString(
                        "Enter new product category:");

        try {
            Product updatedProduct =
                    Product.builder()
                            .id(productId)
                            .name(name)
                            .description(description)
                            .dateOfManufacture(
                                    manufactureDate)
                            .price(price)
                            .category(category)
                            .build();

            boolean updated =
                    inventoryService.updateProduct(
                            updatedProduct);

            if (updated) {
                System.out.println(
                        "Product updated successfully.");
            } else {
                System.out.println(
                        "Product could not be updated.");
            }

        } catch (IllegalArgumentException
                | IllegalStateException e) {

            System.out.println(
                    "Product could not be updated: "
                            + e.getMessage());
        }
    }

    /**
     * Updates the inventory quantity for a product.
     *
     * @param productId the product identifier
     */
    private void updateProductQuantity(int productId) {
        int quantity =
                input.readInt(
                        "Enter the new inventory quantity:",
                        0,
                        Integer.MAX_VALUE);

        boolean updated =
                inventoryService.updateQuantity(
                        productId,
                        quantity);

        if (updated) {
            System.out.println(
                    "Inventory quantity updated successfully.");
        } else {
            System.out.println(
                    "Inventory quantity could not be updated.");
        }
    }

    /**
     * Displays one inventory item in a readable format.
     *
     * @param item the inventory item to display
     */
    private void displayInventoryItem(InventoryItem item) {
        Product product =
                item.getProduct();

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
                "Quantity: "
                        + item.getQuantityInStock());

        System.out.println();
    }
}