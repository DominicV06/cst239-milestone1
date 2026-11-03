package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Comparator;

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
 * Creates the inventory manager using products loaded
 * from the JSON data files.
 */
public InventoryManager() {
    try {
        FileService fileService = new FileService();

        inventory = new ArrayList<>(
                fileService.loadInventoryItems(10)
        );

        System.out.println(
                "Loaded " + inventory.size()
                + " products from JSON."
        );

    } catch (java.io.IOException exception) {
        throw new IllegalStateException(
                "Unable to load product data from JSON.",
                exception
        );
    }
}
   
/**
 * Creates an inventory manager using a provided collection of items.
 * Copies the collection so each manager has its own inventory list.
 *
 * @param initialInventory the items used to initialize the inventory
 * @throws IllegalArgumentException if initialInventory is null
 */
public InventoryManager(List<InventoryItem> initialInventory) {
    if (initialInventory == null) {
        throw new IllegalArgumentException("Initial inventory cannot be null.");
    }

    this.inventory = new ArrayList<>(initialInventory);
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
     * Returns a copy of the inventory sorted by product name.
     * The original inventory order is not changed.
     *
     * @return inventory items sorted by product name
     */
    @Override
    public List<InventoryItem> getInventoryItemsSortedByName() {

        List<InventoryItem> sortedInventory =
                new ArrayList<>(inventory);

        sortedInventory.sort(
                Comparator.comparing(
                        item -> item.getProduct()
                                .getName()
                                .toLowerCase(Locale.ROOT)));

        return List.copyOf(sortedInventory);
    }

    /**
     * Returns a copy of the inventory sorted by manufacture date.
     * The original inventory order is not changed.
     *
     * @return inventory items sorted by manufacture date
     */
    @Override
    public List<InventoryItem> getInventoryItemsSortedByDate() {

        List<InventoryItem> sortedInventory =
                new ArrayList<>(inventory);

        sortedInventory.sort(
                Comparator.comparing(
                        item -> item.getProduct()
                                .getDateOfManufacture()));

        return List.copyOf(sortedInventory);
    }

    /**
     * Returns a copy of the inventory sorted by product price.
     * The original inventory order is not changed.
     *
     * @return inventory items sorted by product price
     */
    @Override
    public List<InventoryItem> getInventoryItemsSortedByPrice() {

        List<InventoryItem> sortedInventory =
                new ArrayList<>(inventory);

        sortedInventory.sort(
                Comparator.comparingDouble(
                        item -> item.getProduct()
                                .getPrice()));

        return List.copyOf(sortedInventory);
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
 * Adds a new inventory item when its product identifier
 * does not already exist.
 *
 * @param item the inventory item to add
 * @return true if the item was added, otherwise false
 */
@Override
public boolean addInventoryItem(InventoryItem item) {

    if (item == null || item.getProduct() == null) {
        return false;
    }

    if (getInventoryItemByProductId(
            item.getProduct().getId()) != null) {

        return false;
    }

    return inventory.add(item);
}

    /**
 * Replaces the product information for an existing
 * inventory item while preserving its quantity.
 *
 * @param updatedProduct the updated product
 * @return true if the product was updated, otherwise false
 */
@Override
public boolean updateProduct(Product updatedProduct) {

    if (updatedProduct == null) {
        return false;
    }

    for (int index = 0; index < inventory.size(); index++) {

        InventoryItem currentItem =
                inventory.get(index);

        if (currentItem.getProduct().getId()
                == updatedProduct.getId()) {

            InventoryItem updatedItem =
                    new InventoryItem(
                            updatedProduct,
                            currentItem.getQuantityInStock());

            inventory.set(index, updatedItem);

            return true;
        }
    }

    return false;
}


    /**
 * Changes the quantity of an existing inventory item.
 *
 * @param productId the product identifier
 * @param quantityInStock the new inventory quantity
 * @return true if the quantity was updated, otherwise false
 */
@Override
public boolean updateQuantity(
        int productId,
        int quantityInStock) {

    if (quantityInStock < 0) {
        return false;
    }

    InventoryItem item =
            getInventoryItemByProductId(productId);

    if (item == null) {
        return false;
    }

    int currentQuantity =
            item.getQuantityInStock();

    if (quantityInStock == currentQuantity) {
        return true;
    }

    if (quantityInStock > currentQuantity) {

        item.increaseQuantity(
                quantityInStock - currentQuantity);

        return true;
    }

    return item.decreaseQuantity(
            currentQuantity - quantityInStock);
}


   /**
 * Removes an inventory item using its product identifier.
 *
 * @param productId the product identifier
 * @return true if the item was removed, otherwise false
 */
@Override
public boolean removeProductById(int productId) {

    InventoryItem item =
            getInventoryItemByProductId(productId);

    if (item == null) {
        return false;
    }

    return inventory.remove(item);
}


    /**
 * Removes every inventory item.
 */
@Override
public void clearInventory() {
    inventory.clear();
}
}
