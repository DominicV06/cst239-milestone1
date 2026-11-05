
package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.InventoryItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

/**
 * Additional unit tests for inventory service behavior.
 * Uses controlled test data instead of production JSON.
 */
class InventoryServiceEdgeCaseTest {

    private Product createProduct(int id, String name) {
        return Product.builder()
                .id(id)
                .name(name)
                .description("Product used for unit testing")
                .dateOfManufacture(LocalDate.of(2026, 1, 15))
                .price(49.99)
                .category("peripherals")
                .build();
    }

    @Test
    void rejectsNullInventoryItem() {
        InventoryManager manager =
                new InventoryManager(List.of());

        assertFalse(manager.addInventoryItem(null));
        assertEquals(0,
                manager.getAllInventoryItems().size());
    }

    @Test
    void rejectsNullProductUpdate() {
        InventoryItem item =
                new InventoryItem(
                        createProduct(901, "Keyboard"), 5);

        InventoryManager manager =
                new InventoryManager(List.of(item));

        assertFalse(manager.updateProduct(null));
        assertEquals("Keyboard",
                manager.getInventoryItemByProductId(901)
                        .getProduct().getName());
    }

    @Test
    void rejectsNegativeQuantity() {
        InventoryItem item =
                new InventoryItem(
                        createProduct(901, "Keyboard"), 5);

        InventoryManager manager =
                new InventoryManager(List.of(item));

        assertFalse(manager.updateQuantity(901, -1));
        assertEquals(5,
                manager.getInventoryItemByProductId(901)
                        .getQuantityInStock());
    }

    @Test
    void emptySearchDoesNotReturnProducts() {
        InventoryItem item =
                new InventoryItem(
                        createProduct(901, "Keyboard"), 5);

        InventoryManager manager =
                new InventoryManager(List.of(item));

        assertTrue(manager.searchProductsByName("").isEmpty());
        assertTrue(manager.searchProductsByName(null).isEmpty());
        assertTrue(manager.searchProductsByDescription(" ").isEmpty());
    }

    @Test
    void updatingProductPreservesStock() {
        InventoryItem item =
                new InventoryItem(
                        createProduct(901, "Keyboard"), 12);

        InventoryManager manager =
                new InventoryManager(List.of(item));

        Product updated =
                createProduct(901, "Updated Keyboard");

        assertTrue(manager.updateProduct(updated));

        InventoryItem result =
                manager.getInventoryItemByProductId(901);

        assertEquals("Updated Keyboard",
                result.getProduct().getName());
        assertEquals(12, result.getQuantityInStock());
    }

    @Test
    void removingUnknownProductKeepsInventory() {
        InventoryItem item =
                new InventoryItem(
                        createProduct(901, "Keyboard"), 5);

        InventoryManager manager =
                new InventoryManager(List.of(item));

        assertFalse(manager.removeProductById(999));
        assertEquals(1,
                manager.getAllInventoryItems().size());
    }
}
