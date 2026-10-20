package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.CartItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.InventoryItem;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

class ShoppingCartTest {

    @Test
    void addsProductToEmptyCart() {

        // Arrange
        Product keyboard = Product.builder()
                .id(901)
                .name("Test Keyboard")
                .description("Mechanical gaming keyboard")
                .dateOfManufacture(LocalDate.of(2026, 1, 15))
                .price(49.99)
                .category("peripherals")
                .build();

        InventoryItem inventoryItem = new InventoryItem(keyboard, 10);

        InventoryManager inventory = new InventoryManager(
                List.of(inventoryItem));

        CartService cart = new ShoppingCart(inventory);

        // Act
        boolean added = cart.addProduct(keyboard, 2);

        // Assert
        assertTrue(added);
        assertEquals(1, cart.getAllCartItems().size());

        CartItem savedItem = cart.getAllCartItems().get(0);

        assertEquals(901, savedItem.getProduct().getId());
        assertEquals(2, savedItem.getQuantity());
        assertEquals(99.98, cart.getCartTotal(), 0.001);

        // Adding to the cart should not reduce inventory yet.
        assertEquals(10,
                inventory.getInventoryItemByProductId(901)
                        .getQuantityInStock());
    }
    
@Test
void addingSameProductTwiceCombinesQuantities() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    InventoryItem inventoryItem = new InventoryItem(keyboard, 10);

    InventoryManager inventory = new InventoryManager(
            List.of(inventoryItem));

    CartService cart = new ShoppingCart(inventory);

    // Act
    boolean firstAdd = cart.addProduct(keyboard, 2);
    boolean secondAdd = cart.addProduct(keyboard, 3);

    // Assert
    assertTrue(firstAdd);
    assertTrue(secondAdd);

    assertEquals(1, cart.getAllCartItems().size());

    CartItem savedItem = cart.getAllCartItems().get(0);

    assertEquals(901, savedItem.getProduct().getId());
    assertEquals(5, savedItem.getQuantity());
    assertEquals(249.95, cart.getCartTotal(), 0.001);

    // Inventory should not change until checkout.
    assertEquals(10,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());
}

@Test
void updatesCartItemQuantity() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    InventoryItem inventoryItem = new InventoryItem(keyboard, 10);

    InventoryManager inventory = new InventoryManager(
            List.of(inventoryItem));

    CartService cart = new ShoppingCart(inventory);
    assertTrue(cart.addProduct(keyboard, 2));

    // Act
    boolean updated = cart.updateQuantity(901, 4);

    // Assert
    assertTrue(updated);
    assertEquals(1, cart.getAllCartItems().size());

    CartItem savedItem = cart.getAllCartItems().get(0);

    assertEquals(901, savedItem.getProduct().getId());
    assertEquals(4, savedItem.getQuantity());
    assertEquals(199.96, cart.getCartTotal(), 0.001);

    // Store inventory should remain unchanged before checkout.
    assertEquals(10,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());
}

@Test
void rejectsNegativeCartQuantity() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    InventoryItem inventoryItem = new InventoryItem(keyboard, 10);

    InventoryManager inventory = new InventoryManager(
            List.of(inventoryItem));

    CartService cart = new ShoppingCart(inventory);
    assertTrue(cart.addProduct(keyboard, 2));

    // Act and Assert
   boolean updated = cart.updateQuantity(901, -3);
   assertFalse(updated);

    // Verify the rejected update did not change the cart.
    assertEquals(1, cart.getAllCartItems().size());
    assertEquals(2, cart.getAllCartItems().get(0).getQuantity());
    assertEquals(99.98, cart.getCartTotal(), 0.001);

    // Inventory remains unchanged.
    assertEquals(10,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());
}

@Test
void zeroQuantityRemovesCartItem() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    InventoryItem inventoryItem = new InventoryItem(keyboard, 10);

    InventoryManager inventory = new InventoryManager(
            List.of(inventoryItem));

    CartService cart = new ShoppingCart(inventory);

    assertTrue(cart.addProduct(keyboard, 2));

    // Act
    boolean updated = cart.updateQuantity(901, 0);

    // Assert
    assertTrue(updated);
    assertEquals(0, cart.getAllCartItems().size());
    assertEquals(0.0, cart.getCartTotal(), 0.001);

    // Removing from the cart should not change inventory.
    assertEquals(10,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());
}

@Test
void removesProductFromCart() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    Product mouse = Product.builder()
            .id(902)
            .name("Test Mouse")
            .description("Wireless optical mouse")
            .dateOfManufacture(LocalDate.of(2026, 2, 10))
            .price(24.99)
            .category("peripherals")
            .build();

    InventoryManager inventory = new InventoryManager(
            List.of(
                    new InventoryItem(keyboard, 10),
                    new InventoryItem(mouse, 8)
            ));

    CartService cart = new ShoppingCart(inventory);

    assertTrue(cart.addProduct(keyboard, 2));
    assertTrue(cart.addProduct(mouse, 3));

    // Act
    boolean removed = cart.removeProductFromCart(901);

    // Assert
    assertTrue(removed);
    assertEquals(1, cart.getAllCartItems().size());

    CartItem remainingItem = cart.getAllCartItems().get(0);

    assertEquals(902, remainingItem.getProduct().getId());
    assertEquals(3, remainingItem.getQuantity());
    assertEquals(74.97, cart.getCartTotal(), 0.001);

    // Removing a cart item does not change store inventory.
    assertEquals(10,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());

    assertEquals(8,
            inventory.getInventoryItemByProductId(902)
                    .getQuantityInStock());
}

@Test
void calculatesCartTotalForMultipleProducts() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    Product mouse = Product.builder()
            .id(902)
            .name("Test Mouse")
            .description("Wireless optical mouse")
            .dateOfManufacture(LocalDate.of(2026, 2, 10))
            .price(24.99)
            .category("peripherals")
            .build();

    InventoryManager inventory = new InventoryManager(
            List.of(
                    new InventoryItem(keyboard, 10),
                    new InventoryItem(mouse, 8)
            ));

    CartService cart = new ShoppingCart(inventory);

    // Act
    boolean keyboardAdded = cart.addProduct(keyboard, 2);
    boolean mouseAdded = cart.addProduct(mouse, 3);

    double total = cart.getCartTotal();

    // Assert
    assertTrue(keyboardAdded);
    assertTrue(mouseAdded);
    assertEquals(2, cart.getAllCartItems().size());

    // (2 x 49.99) + (3 x 24.99) = 174.95
    assertEquals(174.95, total, 0.001);

    // Adding products to the cart does not reduce inventory.
    assertEquals(10,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());

    assertEquals(8,
            inventory.getInventoryItemByProductId(902)
                    .getQuantityInStock());
}

@Test
void emptyCartHasNoItemsAndZeroTotal() {

    // Arrange
    InventoryManager inventory = new InventoryManager(List.of());
    CartService cart = new ShoppingCart(inventory);

    // Act
    List<CartItem> items = cart.getAllCartItems();
    double total = cart.getCartTotal();

    // Assert
    assertNotNull(items);
    assertTrue(items.isEmpty());
    assertEquals(0, items.size());
    assertEquals(0.0, total, 0.001);
}

@Test
void successfulCheckoutReducesInventory() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    InventoryManager inventory = new InventoryManager(
            List.of(new InventoryItem(keyboard, 10)));

    CartService cart = new ShoppingCart(inventory);

    assertTrue(cart.addProduct(keyboard, 3));

    // Verify starting inventory.
    assertEquals(10,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());

    // Act
    boolean completed = cart.checkout();

    // Assert
    assertTrue(completed);

    // Purchasing three units should leave seven in stock.
    assertEquals(7,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());
}

@Test
void successfulCheckoutClearsCart() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    InventoryManager inventory = new InventoryManager(
            List.of(new InventoryItem(keyboard, 10)));

    CartService cart = new ShoppingCart(inventory);

    assertTrue(cart.addProduct(keyboard, 2));
    assertEquals(1, cart.getAllCartItems().size());
    assertEquals(99.98, cart.getCartTotal(), 0.001);

    // Act
    boolean completed = cart.checkout();

    // Assert
    assertTrue(completed);

    // The shopping cart should be empty after checkout.
    assertTrue(cart.getAllCartItems().isEmpty());
    assertEquals(0, cart.getAllCartItems().size());
    assertEquals(0.0, cart.getCartTotal(), 0.001);

    // Inventory should reflect the completed purchase.
    assertEquals(8,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());
}

@Test
void insufficientInventoryPreventsCheckout() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    InventoryManager inventory = new InventoryManager(
            List.of(new InventoryItem(keyboard, 5)));

    CartService cart = new ShoppingCart(inventory);

    // Add three keyboards while sufficient stock exists.
    assertTrue(cart.addProduct(keyboard, 3));

    // Simulate another purchase reducing available stock.
    assertTrue(inventory.updateQuantity(901, 2));

    // Act
    boolean completed = cart.checkout();

    // Assert
    assertFalse(completed);

    // Failed checkout must not reduce inventory.
    assertEquals(2,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());

    // The cart should retain the original items.
    assertEquals(1, cart.getAllCartItems().size());
    assertEquals(3, cart.getAllCartItems().get(0).getQuantity());
    assertEquals(149.97, cart.getCartTotal(), 0.001);
}

@Test
void failedCheckoutDoesNotPartiallyReduceInventory() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    Product mouse = Product.builder()
            .id(902)
            .name("Test Mouse")
            .description("Wireless optical mouse")
            .dateOfManufacture(LocalDate.of(2026, 2, 10))
            .price(24.99)
            .category("peripherals")
            .build();

    InventoryManager inventory = new InventoryManager(
            List.of(
                    new InventoryItem(keyboard, 10),
                    new InventoryItem(mouse, 5)
            ));

    CartService cart = new ShoppingCart(inventory);

    assertTrue(cart.addProduct(keyboard, 3));
    assertTrue(cart.addProduct(mouse, 4));

    // Reduce mouse stock after the items are added to the cart.
    assertTrue(inventory.updateQuantity(902, 2));

    // Act
    boolean completed = cart.checkout();

    // Assert
    assertFalse(completed);

    // Neither inventory quantity should change during failed checkout.
    assertEquals(10,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());

    assertEquals(2,
            inventory.getInventoryItemByProductId(902)
                    .getQuantityInStock());

    // Both cart items should remain available for correction.
    assertEquals(2, cart.getAllCartItems().size());

    assertEquals(3, cart.getAllCartItems().get(0).getQuantity());
    assertEquals(4, cart.getAllCartItems().get(1).getQuantity());

    // The failed checkout should not change the cart total.
    assertEquals(249.93, cart.getCartTotal(), 0.001);
}

@Test
void emptyCartCheckoutReturnsFalse() {

    // Arrange
    Product keyboard = Product.builder()
            .id(901)
            .name("Test Keyboard")
            .description("Mechanical gaming keyboard")
            .dateOfManufacture(LocalDate.of(2026, 1, 15))
            .price(49.99)
            .category("peripherals")
            .build();

    InventoryManager inventory = new InventoryManager(
            List.of(new InventoryItem(keyboard, 10)));

    CartService cart = new ShoppingCart(inventory);

    // Verify the cart starts empty.
    assertTrue(cart.getAllCartItems().isEmpty());

    // Act
    boolean completed = cart.checkout();

    // Assert
    assertFalse(completed);

    // The cart should remain empty.
    assertTrue(cart.getAllCartItems().isEmpty());
    assertEquals(0.0, cart.getCartTotal(), 0.001);

    // Inventory must remain unchanged.
    assertEquals(10,
            inventory.getInventoryItemByProductId(901)
                    .getQuantityInStock());
}

}
