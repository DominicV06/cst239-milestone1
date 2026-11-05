
package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.gcu.cst239.vatala.dominic.milestone_app.dto.ProductDto;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;

/**
 * Tests the conversion of Product objects into JSON DTOs.
 */
class ProductDtoMappingTest {

    private Product createProduct() {
        return Product.builder()
                .id(901)
                .name("Gaming Keyboard")
                .description("Mechanical gaming keyboard")
                .dateOfManufacture(LocalDate.of(2026, 3, 15))
                .price(79.99)
                .category("peripherals")
                .build();
    }

    @Test
    void mapsProductFieldsCorrectly() {
        FileService fileService = new FileService();
        Product product = createProduct();

        ProductDto dto = fileService.mapToDto(product);

        assertEquals(901, dto.id());
        assertEquals("Gaming Keyboard", dto.name());
        assertEquals("Mechanical gaming keyboard",
                dto.description());
        assertEquals(79.99, dto.price(), 0.001);
        assertEquals("peripherals", dto.category());
    }

    @Test
    void convertsManufactureDateToIsoFormat() {
        FileService fileService = new FileService();

        ProductDto dto =
                fileService.mapToDto(createProduct());

        assertEquals("2026-03-15",
                dto.dateOfManufacture());
    }

    @Test
    void rejectsNullProductMapping() {
        FileService fileService = new FileService();

        assertThrows(IllegalArgumentException.class,
                () -> fileService.mapToDto(null));
    }
}
