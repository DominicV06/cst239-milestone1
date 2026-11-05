
package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

/**
 * Tests JSON loading and saving using temporary files.
 * Production data is not modified.
 */
class FileServicePersistenceTest {

    @TempDir
    Path tempDir;

    private FileService createFileService() throws IOException {
        Path productsPath = tempDir.resolve("products.json");
        Path categoriesPath = tempDir.resolve("categories.json");

        Files.writeString(categoriesPath, """
            [
              {
                "category": "peripherals",
                "description": "Computer accessories"
              },
              {
                "category": "audio",
                "description": "Headphones and speakers"
              }
            ]
            """);

        Files.writeString(productsPath, "[]");

        return new FileService(productsPath, categoriesPath);
    }

    private Product createProduct(
            int id, String name, double price) {

        return Product.builder()
                .id(id)
                .name(name)
                .description("Test product description")
                .dateOfManufacture(LocalDate.of(2026, 3, 15))
                .price(price)
                .category("peripherals")
                .build();
    }

    @Test
    void loadsExpectedNumberOfProducts() throws IOException {
        FileService service = createFileService();

        service.saveProducts(List.of(
                createProduct(901, "Keyboard", 49.99),
                createProduct(902, "Mouse", 29.99),
                createProduct(903, "Headset", 79.99)
        ));

        List<Product> loaded = service.loadProducts();

        assertEquals(3, loaded.size());
    }

    @Test
    void savesAndReloadsProductValues() throws IOException {
        FileService service = createFileService();

        Product original =
                createProduct(901, "Gaming Keyboard", 79.99);

        service.saveProducts(List.of(original));

        List<Product> loaded = service.loadProducts();

        assertEquals(1, loaded.size());

        Product result = loaded.get(0);

        assertEquals(original.getId(), result.getId());
        assertEquals(original.getName(), result.getName());
        assertEquals(original.getDescription(),
                result.getDescription());
        assertEquals(original.getDateOfManufacture(),
                result.getDateOfManufacture());
        assertEquals(original.getPrice(),
                result.getPrice(), 0.001);
        assertEquals(original.getCategory(),
                result.getCategory());
    }

    @Test
    void savedChangesPersistAfterReload() throws IOException {
        FileService service = createFileService();

        service.saveProducts(List.of(
                createProduct(901, "Original Keyboard", 49.99)
        ));

        service.saveProducts(List.of(
                createProduct(901, "Updated Keyboard", 89.99)
        ));

        List<Product> loaded = service.loadProducts();

        assertEquals(1, loaded.size());
        assertEquals("Updated Keyboard",
                loaded.get(0).getName());
        assertEquals(89.99,
                loaded.get(0).getPrice(), 0.001);
    }

    @Test
    void malformedJsonProducesError() throws IOException {
        FileService service = createFileService();

        Files.writeString(
                service.getProductsPath(),
                "{ this is not valid JSON"
        );

        assertThrows(IOException.class,
                service::loadProducts);
    }

    @Test
    void unknownCategoryProducesError() throws IOException {
        FileService service = createFileService();

        Files.writeString(
                service.getProductsPath(),
                """
                [
                  {
                    "id": 901,
                    "name": "Keyboard",
                    "description": "Test product",
                    "dateOfManufacture": "2026-03-15",
                    "price": 49.99,
                    "category": "unknown-category"
                  }
                ]
                """
        );

        assertThrows(IllegalArgumentException.class,
                service::loadProducts);
    }

    @Test
    void invalidDateProducesError() throws IOException {
        FileService service = createFileService();

        Files.writeString(
                service.getProductsPath(),
                """
                [
                  {
                    "id": 901,
                    "name": "Keyboard",
                    "description": "Test product",
                    "dateOfManufacture": "not-a-date",
                    "price": 49.99,
                    "category": "peripherals"
                  }
                ]
                """
        );

        assertThrows(IllegalArgumentException.class,
                service::loadProducts);
    }
}
