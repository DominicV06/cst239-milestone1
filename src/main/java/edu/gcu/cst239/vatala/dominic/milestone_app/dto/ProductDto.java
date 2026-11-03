package edu.gcu.cst239.vatala.dominic.milestone_app.dto;

/**
 * Represents product data stored in a JSON file.
 * This DTO is separate from the main Product class.
 */
public record ProductDto(
        int id,
        String name,
        String description,
        String dateOfManufacture,
        double price,
        String category) {
}

