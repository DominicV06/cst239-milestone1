
package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

/**
 * Tests product validation before saving JSON data.
 */
class FileServiceValidationTest {

    @TempDir
    Path tempDir;

    private Product createProduct(int id, String category) {
        return Product.builder()
                .id(id)
                .name("Test Keyboard")
                .description("Product used for testing")
                .dateOfManufacture(LocalDate.of(2026, 3, 15))
                .price(49.99)
                .category(category)
                .build();
    }

    private FileService createFileService() throws Exception {
        Path productsPath = tempDir.resolve("products.json");
        Path categoriesPath = tempDir.resolve("categories.json");

        Files.writeString(productsPath, "[]");

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

        return new FileService(productsPath, categoriesPath);
    }

    @Test
    void acceptsValidProductCategory() throws Exception {
        FileService fileService = createFileService();

        fileService.saveProducts(
                List.of(createProduct(901, "peripherals")));

        List<Product> loaded = fileService.loadProducts();

        assertEquals(1, loaded.size());
        assertEquals("peripherals", loaded.get(0).getCategory());
    }

    @Test
    void rejectsUnknownProductCategory() throws Exception {
        FileService fileService = createFileService();

        Product invalid =
                createProduct(901, "invalid-category");

        assertThrows(IllegalArgumentException.class,
                () -> fileService.saveProducts(List.of(invalid)));
    }

    @Test
    void rejectsDuplicateProductIds() throws Exception {
        FileService fileService = createFileService();

        Product first = createProduct(901, "peripherals");
        Product second = createProduct(901, "audio");

        assertThrows(IllegalArgumentException.class,
                () -> fileService.saveProducts(
                        List.of(first, second)));
    }

    @Test
    void failedSaveDoesNotOverwriteExistingFile()
            throws Exception {

        FileService fileService = createFileService();

        fileService.saveProducts(
                List.of(createProduct(901, "peripherals")));

        String originalJson =
                Files.readString(fileService.getProductsPath());

        Product invalid =
                createProduct(902, "invalid-category");

        assertThrows(IllegalArgumentException.class,
                () -> fileService.saveProducts(
                        List.of(createProduct(901, "peripherals"),
                                invalid)));

        String afterFailure =
                Files.readString(fileService.getProductsPath());

        assertEquals(originalJson, afterFailure);
    }
}

