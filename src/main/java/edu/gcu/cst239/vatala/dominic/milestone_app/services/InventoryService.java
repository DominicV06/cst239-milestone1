package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import java.util.List;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.InventoryItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

/**
 * Defines the operations used to manage storefront inventory.
 * Implementations are responsible for retrieving, searching,
 * adding, updating, and removing inventory items.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public interface InventoryService {

    /**
     * Returns a read-only copy of the current inventory.
     *
     * @return all inventory items
     */
    List<InventoryItem> getAllInventoryItems();

    /**
     * Finds an inventory item using its product identifier.
     *
     * @param productId the product identifier
     * @return the matching item, or null if no match is found
     */
    InventoryItem getInventoryItemByProductId(int productId);

    /**
     * Searches for inventory items whose product names contain
     * the supplied search term.
     *
     * @param searchTerm all or part of a product name
     * @return matching inventory items
     */
    List<InventoryItem> searchProductsByName(String searchTerm);

    /**
     * Searches for inventory items whose product descriptions contain
     * the supplied search term.
     *
     * @param searchTerm all or part of a product description
     * @return matching inventory items
     */
    List<InventoryItem> searchProductsByDescription(String searchTerm);

    /**
     * Adds a new item to the inventory.
     *
     * @param item the inventory item to add
     * @return true if the item was added, otherwise false
     */
    boolean addInventoryItem(InventoryItem item);

    /**
     * Replaces the product information for an existing inventory item.
     *
     * @param updatedProduct the updated product
     * @return true if the product was updated, otherwise false
     */
    boolean updateProduct(Product updatedProduct);

    /**
     * Changes the quantity of an existing inventory item.
     *
     * @param productId the product identifier
     * @param quantityInStock the new quantity
     * @return true if the quantity was updated, otherwise false
     */
    boolean updateQuantity(int productId, int quantityInStock);

    /**
     * Removes the inventory item associated with a product identifier.
     *
     * @param productId the product identifier
     * @return true if the item was removed, otherwise false
     */
    boolean removeProductById(int productId);

    /**
     * Removes every inventory item.
     */
    void clearInventory();
}