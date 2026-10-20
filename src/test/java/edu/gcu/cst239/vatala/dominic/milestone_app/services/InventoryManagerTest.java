
package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.InventoryItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

class InventoryManagerTest {

    @Test
    void separateManagersHaveIndependentInventories() {

        // Arrange
        InventoryManager firstManager = new InventoryManager(List.of());
        InventoryManager secondManager = new InventoryManager(List.of());

        Product testProduct = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Keyboard used for inventory testing")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

        InventoryItem testItem = new InventoryItem(testProduct, 5);

        
        // Act
        boolean added = firstManager.addInventoryItem(testItem);

        // Assert
        assertTrue(added);
        assertEquals(1, firstManager.getAllInventoryItems().size());
        assertEquals(0, secondManager.getAllInventoryItems().size());

    }
}
