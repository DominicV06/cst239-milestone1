
package edu.gcu.cst239.vatala.dominic.milestone_app.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.gcu.cst239.vatala.dominic.milestone_app.dto.ProductDto;
import edu.gcu.cst239.vatala.dominic.milestone_app.dto.ProductCategoryDto;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.Product;
import edu.gcu.cst239.vatala.dominic.milestone_app.models.InventoryItem;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Loads product and category information from JSON files.
 * Converts JSON DTO records into domain objects.
 */
public class FileService {

    private final ObjectMapper mapper;
    private final Path productsPath;
    private final Path categoriesPath;

    /**
     * Uses the project's default JSON files.
     */
    public FileService() {
        this(
            Path.of("src/main/resources/products.json"),
            Path.of("src/main/resources/categories.json")
        );
    }

    /**
     * Allows different paths to be used for testing.
     */
    public FileService(Path productsPath, Path categoriesPath) {
        if (productsPath == null || categoriesPath == null) {
            throw new IllegalArgumentException(
                "JSON file paths are required.");
        }

        this.mapper = new ObjectMapper();
        this.productsPath = productsPath;
        this.categoriesPath = categoriesPath;
    }

    /**
     * Loads and validates category definitions.
     */
    public List<ProductCategoryDto> loadCategories()
            throws IOException {

        List<ProductCategoryDto> categories =
            mapper.readValue(
                categoriesPath.toFile(),
                new TypeReference<List<ProductCategoryDto>>() {}
            );

        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException(
                "Category data cannot be empty.");
        }

        Set<String> keys = new HashSet<>();

        for (ProductCategoryDto category : categories) {
            if (category == null ||
                category.category() == null ||
                category.category().isBlank() ||
                category.description() == null ||
                category.description().isBlank()) {

                throw new IllegalArgumentException(
                    "Invalid category data.");
            }

            if (!keys.add(category.category())) {
                throw new IllegalArgumentException(
                    "Duplicate category: " + category.category());
            }
        }

        return categories;
    }

    /**
     * Loads JSON products and converts them into Product objects.
     * Category data is loaded first.
     */
    public List<Product> loadProducts() throws IOException {

        List<ProductCategoryDto> categories = loadCategories();

        Set<String> validCategories = new HashSet<>();

        for (ProductCategoryDto category : categories) {
            validCategories.add(category.category());
        }

        List<ProductDto> productDtos =
            mapper.readValue(
                productsPath.toFile(),
                new TypeReference<List<ProductDto>>() {}
            );

        if (productDtos == null) {
            throw new IllegalArgumentException(
                "Product JSON must contain an array.");
        }

        List<Product> products = new ArrayList<>();
        Set<Integer> productIds = new HashSet<>();

        for (ProductDto dto : productDtos) {

            if (dto == null) {
                throw new IllegalArgumentException(
                    "Product record cannot be null.");
            }

            if (!productIds.add(dto.id())) {
                throw new IllegalArgumentException(
                    "Duplicate product ID: " + dto.id());
            }

            products.add(mapToProduct(dto, validCategories));
        }

        return products;
    }

    /**
     * Converts a validated DTO into the existing Product model.
     */
    private Product mapToProduct(
            ProductDto dto,
            Set<String> validCategories) {

        if (dto.id() <= 0 ||
            dto.name() == null ||
            dto.name().isBlank() ||
            dto.description() == null ||
            dto.description().isBlank() ||
            dto.dateOfManufacture() == null ||
            dto.dateOfManufacture().isBlank() ||
            !Double.isFinite(dto.price()) ||
            dto.price() <= 0) {

            throw new IllegalArgumentException(
                "Invalid product data for ID: " + dto.id());
        }

        if (dto.category() == null ||
            !validCategories.contains(dto.category())) {

            throw new IllegalArgumentException(
                "Unknown category for product ID: " + dto.id());
        }

        LocalDate manufactureDate;

        try {
            manufactureDate =
                LocalDate.parse(dto.dateOfManufacture());

            if (!manufactureDate.toString()
                    .equals(dto.dateOfManufacture())) {
                throw new IllegalArgumentException(
                    "Date must use yyyy-MM-dd format.");
            }

        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                "Invalid manufacture date for product ID: "
                + dto.id(), exception);
        }

        return Product.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .dateOfManufacture(manufactureDate)
                .price(dto.price())
                .category(dto.category())
                .build();
    }

    /**
     * Loads products into inventory items.
     *
     * The JSON contract does not contain stock quantities.
     * Each loaded product starts with the supplied quantity.
     */
    public List<InventoryItem> loadInventoryItems(
            int startingQuantity) throws IOException {

        if (startingQuantity < 0) {
            throw new IllegalArgumentException(
                "Starting quantity cannot be negative.");
        }

        List<InventoryItem> inventory = new ArrayList<>();

        for (Product product : loadProducts()) {
            inventory.add(
                new InventoryItem(product, startingQuantity)
            );
        }

        return inventory;
    }

    
    /**
    * Returns the location of the product JSON file.
    */
    public Path getProductsPath() {
    return productsPath;
        }


    /**
    * Converts a domain Product into a JSON DTO.
    */
    public ProductDto mapToDto(Product product) {
    if (product == null) {
        throw new IllegalArgumentException(
            "Product cannot be null.");
    }

    return new ProductDto(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getDateOfManufacture().toString(),
        product.getPrice(),
        product.getCategory()
    );
    }

    

/**
 * Saves products to JSON after validating their data.
 * Invalid categories and duplicate IDs are rejected
 * before the existing file is overwritten.
 */
public void saveProducts(List<Product> products)
        throws IOException {

    if (products == null) {
        throw new IllegalArgumentException(
            "Product list cannot be null.");
    }

    // Load the valid categories from categories.json.
    List<ProductCategoryDto> categories = loadCategories();

    Set<String> validCategories = new HashSet<>();

    for (ProductCategoryDto category : categories) {
        validCategories.add(category.category());
    }

    List<ProductDto> productDtos = new ArrayList<>();
    Set<Integer> productIds = new HashSet<>();

    for (Product product : products) {

        ProductDto dto = mapToDto(product);

        // Reject duplicate product IDs.
        if (!productIds.add(dto.id())) {
            throw new IllegalArgumentException(
                "Duplicate product ID: " + dto.id());
        }

        // Reject categories that are not defined.
        if (dto.category() == null ||
                !validCategories.contains(dto.category())) {

            throw new IllegalArgumentException(
                "Unknown category for product ID: "
                + dto.id());
        }

        // Validate product data and manufacture date.
        mapToProduct(dto, validCategories);

        productDtos.add(dto);
    }

    // Write only after every product has passed validation.
    mapper.writerWithDefaultPrettyPrinter()
          .writeValue(productsPath.toFile(), productDtos);
}



}

