package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.InventoryItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

/**
 * Manages the inventory for a technology-accessories store.
 * The manager owns the inventory collection and provides
 * inventory query and management operations.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class InventoryManager implements InventoryService {

    private final List<InventoryItem> inventory;

    /**
     * Creates the inventory manager and initializes
     * the starting storefront inventory.
     */
    public InventoryManager() {
        inventory = new ArrayList<>();
        initializeInventory();
    }

    /**
     * Creates the starting inventory for the technology store.
     */
    private void initializeInventory() {

        Product keyboard = Product.builder()
                .id(101)
                .name("Mechanical Keyboard")
                .description(
                        "Compact hot-swappable mechanical keyboard with RGB backlight")
                .dateOfManufacture(LocalDate.of(2026, 6, 18))
                .price(79.99)
                .category("peripherals")
                .build();

        Product mouse = Product.builder()
                .id(102)
                .name("Wireless Gaming Mouse")
                .description(
                        "Lightweight rechargeable mouse with adjustable DPI")
                .dateOfManufacture(LocalDate.of(2026, 5, 27))
                .price(49.99)
                .category("peripherals")
                .build();

        Product dock = Product.builder()
                .id(103)
                .name("USB-C Dock")
                .description(
                        "Seven-port USB-C dock with HDMI, USB, and Ethernet")
                .dateOfManufacture(LocalDate.of(2026, 4, 12))
                .price(64.50)
                .category("connectivity")
                .build();

        Product headset = Product.builder()
                .id(104)
                .name("Noise-Canceling Headset")
                .description(
                        "Over-ear USB headset with microphone and active noise canceling")
                .dateOfManufacture(LocalDate.of(2026, 3, 30))
                .price(89.00)
                .category("audio")
                .build();

        Product ssd = Product.builder()
                .id(105)
                .name("Portable SSD 1TB")
                .description(
                        "Pocket-size USB-C solid-state drive with one terabyte capacity")
                .dateOfManufacture(LocalDate.of(2026, 2, 22))
                .price(99.95)
                .category("storage")
                .build();

        inventory.add(new InventoryItem(keyboard, 14));
        inventory.add(new InventoryItem(mouse, 22));
        inventory.add(new InventoryItem(dock, 11));
        inventory.add(new InventoryItem(headset, 9));
        inventory.add(new InventoryItem(ssd, 16));
    }

    /**
     * Returns a read-only copy of the current inventory.
     *
     * @return all inventory items
     */
    @Override
    public List<InventoryItem> getAllInventoryItems() {
        return List.copyOf(inventory);
    }

    /**
     * Finds an inventory item by its product identifier.
     *
     * @param productId the product identifier
     * @return the matching inventory item, or null if not found
     */
    @Override
    public InventoryItem getInventoryItemByProductId(int productId) {

        for (InventoryItem item : inventory) {
            if (item.getProduct().getId() == productId) {
                return item;
            }
        }

        return null;
    }

    /**
     * Searches inventory using all or part of a product name.
     * Matching is case-insensitive.
     *
     * @param searchTerm all or part of a product name
     * @return every matching inventory item
     */
    @Override
    public List<InventoryItem> searchProductsByName(String searchTerm) {

        if (searchTerm == null || searchTerm.isBlank()) {
            return List.of();
        }

        String normalizedTerm =
                searchTerm.trim().toLowerCase(Locale.ROOT);

        List<InventoryItem> matches = new ArrayList<>();

        for (InventoryItem item : inventory) {

            String productName =
                    item.getProduct().getName();

            if (productName.toLowerCase(Locale.ROOT)
                    .contains(normalizedTerm)) {

                matches.add(item);
            }
        }

        return matches;
    }

    /**
     * Searches inventory using all or part of a product description.
     * Matching is case-insensitive.
     *
     * @param searchTerm all or part of a product description
     * @return every matching inventory item
     */
    @Override
    public List<InventoryItem> searchProductsByDescription(
            String searchTerm) {

        if (searchTerm == null || searchTerm.isBlank()) {
            return List.of();
        }

        String normalizedTerm =
                searchTerm.trim().toLowerCase(Locale.ROOT);

        List<InventoryItem> matches = new ArrayList<>();

        for (InventoryItem item : inventory) {

            String description =
                    item.getProduct().getDescription();

            if (description.toLowerCase(Locale.ROOT)
                    .contains(normalizedTerm)) {

                matches.add(item);
            }
        }

        return matches;
    }

    /**
     * Adds an inventory item.
     * This operation is implemented in the inventory CRUD branch.
     *
     * @param item the inventory item to add
     * @return false until this operation is implemented
     */
    @Override
    public boolean addInventoryItem(InventoryItem item) {
        return false;
    }

    /**
     * Updates existing product information.
     * This operation is implemented in the inventory CRUD branch.
     *
     * @param updatedProduct the updated product
     * @return false until this operation is implemented
     */
    @Override
    public boolean updateProduct(Product updatedProduct) {
        return false;
    }

    /**
     * Updates an inventory quantity.
     * This operation is implemented in the inventory CRUD branch.
     *
     * @param productId the product identifier
     * @param quantityInStock the new quantity
     * @return false until this operation is implemented
     */
    @Override
    public boolean updateQuantity(
            int productId,
            int quantityInStock) {

        return false;
    }

    /**
     * Removes an inventory item by product identifier.
     * This operation is implemented in the inventory CRUD branch.
     *
     * @param productId the product identifier
     * @return false until this operation is implemented
     */
    @Override
    public boolean removeProductById(int productId) {
        return false;
    }

    /**
     * Clears all inventory.
     * This operation is implemented in the inventory CRUD branch.
     */
    @Override
    public void clearInventory() {
        // Placeholder for Branch 7.
    }
}