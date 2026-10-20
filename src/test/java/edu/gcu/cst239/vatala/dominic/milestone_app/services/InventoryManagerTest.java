
package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

@Test
void returnsAllInventoryItems() {

    // Arrange
    InventoryItem keyboard = createTestItem(
            901, "Test Keyboard", "Mechanical gaming keyboard", 49.99, 5);

    InventoryItem mouse = createTestItem(
            902, "Test Mouse", "Wireless optical mouse", 24.99, 8);

    InventoryItem monitor = createTestItem(
            903, "Test Monitor", "27-inch LED display", 179.99, 3);

    InventoryManager manager = new InventoryManager(
            List.of(keyboard, mouse, monitor));

    // Act
    List<InventoryItem> items = manager.getAllInventoryItems();

    // Assert
    assertEquals(3, items.size());
    assertEquals(901, items.get(0).getProduct().getId());
    assertEquals(902, items.get(1).getProduct().getId());
    assertEquals(903, items.get(2).getProduct().getId());
}


@Test
void findsExistingProductById() {

    // Arrange
    InventoryItem keyboard = createTestItem(
            901, "Test Keyboard", "Mechanical gaming keyboard", 49.99, 5);

    InventoryItem mouse = createTestItem(
            902, "Test Mouse", "Wireless optical mouse", 24.99, 8);

    InventoryManager manager = new InventoryManager(
            List.of(keyboard, mouse));

    // Act
    InventoryItem foundItem = manager.getInventoryItemByProductId(902);

    // Assert
    assertNotNull(foundItem);
    assertEquals(902, foundItem.getProduct().getId());
    assertEquals("Test Mouse", foundItem.getProduct().getName());
    assertEquals(8, foundItem.getQuantityInStock());
}

@Test
void returnsNullForUnknownProductId() {

    // Arrange
    InventoryItem keyboard = createTestItem(
            901, "Test Keyboard", "Mechanical gaming keyboard", 49.99, 5);

    InventoryItem mouse = createTestItem(
            902, "Test Mouse", "Wireless optical mouse", 24.99, 8);

    InventoryManager manager = new InventoryManager(
            List.of(keyboard, mouse));

    // Act
    InventoryItem foundItem = manager.getInventoryItemByProductId(999);

    // Assert
    assertNull(foundItem);
    assertEquals(2, manager.getAllInventoryItems().size());
}

@Test
void findsProductsByName() {

    // Arrange
    InventoryItem keyboard = createTestItem(
            901, "Test Keyboard", "Mechanical gaming keyboard", 49.99, 5);

    InventoryItem mouse = createTestItem(
            902, "Test Mouse", "Wireless optical mouse", 24.99, 8);

    InventoryItem monitor = createTestItem(
            903, "Test Monitor", "27-inch LED display", 179.99, 3);

    InventoryManager manager = new InventoryManager(
            List.of(keyboard, mouse, monitor));

    // Act
    List<InventoryItem> results = manager.searchProductsByName("Mouse");

    // Assert
    assertEquals(1, results.size());
    assertEquals(902, results.get(0).getProduct().getId());
    assertEquals("Test Mouse", results.get(0).getProduct().getName());
}

@Test
void findsProductsByDescription() {

    // Arrange
    InventoryItem keyboard = createTestItem(
            901, "Test Keyboard", "Mechanical gaming keyboard", 49.99, 5);

    InventoryItem mouse = createTestItem(
            902, "Test Mouse", "Wireless optical mouse", 24.99, 8);

    InventoryItem monitor = createTestItem(
            903, "Test Monitor", "27-inch LED display", 179.99, 3);

    InventoryManager manager = new InventoryManager(
            List.of(keyboard, mouse, monitor));

    // Act
    List<InventoryItem> results =
            manager.searchProductsByDescription("optical");

    // Assert
    assertEquals(1, results.size());
    assertEquals(902, results.get(0).getProduct().getId());
    assertEquals("Wireless optical mouse",
            results.get(0).getProduct().getDescription());
}

@Test
void searchesProductsWithoutCaseSensitivity() {

    // Arrange
    InventoryItem keyboard = createTestItem(
            901, "Test Keyboard", "Mechanical Gaming Keyboard", 49.99, 5);

    InventoryItem mouse = createTestItem(
            902, "Test Mouse", "Wireless Optical Mouse", 24.99, 8);

    InventoryManager manager = new InventoryManager(
            List.of(keyboard, mouse));

    // Act
    List<InventoryItem> nameResults =
            manager.searchProductsByName("kEyBoArD");

    List<InventoryItem> descriptionResults =
            manager.searchProductsByDescription("oPtIcAl");

    // Assert
    assertEquals(1, nameResults.size());
    assertEquals(901, nameResults.get(0).getProduct().getId());

    assertEquals(1, descriptionResults.size());
    assertEquals(902, descriptionResults.get(0).getProduct().getId());
}




private InventoryItem createTestItem(
        int id, String name, String description,
        double price, int quantity) {

    Product product = Product.builder()
            .id(id)
            .name(name)
            .description(description)
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(price)
            .category("peripherals")
            .build();

    return new InventoryItem(product, quantity);
}

}
