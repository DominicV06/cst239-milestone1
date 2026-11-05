
package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

/**
 * Verifies the generated production JSON data.
 * This test only reads files and never changes them.
 */
class ProductionJsonDataTest {

    @Test
    void productionJsonContains100ValidProducts()
            throws IOException {

        FileService fileService = new FileService(
                Path.of("src/main/resources/products.json"),
                Path.of("src/main/resources/categories.json")
        );

        List<Product> products = fileService.loadProducts();

        assertEquals(100, products.size(),
                "Production JSON must contain 100 products.");

        Set<Integer> ids = new HashSet<>();

        for (Product product : products) {
            assertTrue(ids.add(product.getId()),
                    "Duplicate product ID: " + product.getId());

            assertTrue(product.getId() > 0);
            assertFalse(product.getName().isBlank());
            assertFalse(product.getDescription().isBlank());
            assertNotNull(product.getDateOfManufacture());
            assertTrue(product.getPrice() > 0);
            assertFalse(product.getCategory().isBlank());
        }
    }
}
