package edu.gcu.cst239.vatala.dominic.milestone_app.models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a product that can be sold through the storefront.
 * Product objects are immutable and are created using the nested Builder.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class Product {

    private final int id;
    private final String name;
    private final String description;
    private final LocalDate dateOfManufacture;
    private final double price;
    private final String category;

    /**
     * Creates a Product using the values stored in a Builder.
     *
     * @param builder the builder containing the product information
     */
    private Product(Builder builder) {
        id = builder.id;
        name = builder.name;
        description = builder.description;
        dateOfManufacture = builder.dateOfManufacture;
        price = builder.price;
        category = builder.category;
    }

    /**
     * Creates a new Builder used to construct a Product.
     *
     * @return a new Product Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the product identifier.
     *
     * @return the product identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the date the product was manufactured.
     *
     * @return the date of manufacture
     */
    public LocalDate getDateOfManufacture() {
        return dateOfManufacture;
    }

    /**
     * Returns the product price.
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the product category.
     *
     * @return the product category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns a readable representation of the product.
     *
     * @return a string containing the product information
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateOfManufacture=" + dateOfManufacture +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }

    /**
     * Compares products using their product identifiers.
     *
     * @param object the object to compare with this product
     * @return true when both products have the same identifier
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Product product)) {
            return false;
        }

        return id == product.id;
    }

    /**
     * Returns a hash code based on the product identifier.
     *
     * @return the product hash code
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Builds validated Product objects using named methods.
     */
    public static class Builder {

        private int id;
        private String name;
        private String description;
        private LocalDate dateOfManufacture;
        private double price;
        private String category = "no-category";

        /**
         * Creates a new Builder.
         */
        private Builder() {
        }

        /**
         * Sets the product identifier.
         *
         * @param id the product identifier
         * @return this Builder
         */
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the product name.
         *
         * @param name the product name
         * @return this Builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the product description.
         *
         * @param description the product description
         * @return this Builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the date the product was manufactured.
         *
         * @param dateOfManufacture the date of manufacture
         * @return this Builder
         */
        public Builder dateOfManufacture(LocalDate dateOfManufacture) {
            this.dateOfManufacture = dateOfManufacture;
            return this;
        }

        /**
         * Sets the product price.
         *
         * @param price the product price
         * @return this Builder
         */
        public Builder price(double price) {
            this.price = price;
            return this;
        }

        /**
         * Sets the product category.
         *
         * @param category the product category
         * @return this Builder
         */
        public Builder category(String category) {
            this.category = category;
            return this;
        }

        /**
         * Validates the Builder values and creates the Product.
         *
         * @return the completed Product
         * @throws IllegalStateException if required product information is invalid
         */
        public Product build() {
            validate();
            return new Product(this);
        }

        /**
         * Validates the values stored in the Builder.
         */
        private void validate() {
            if (id < 1) {
                throw new IllegalStateException(
                        "Product id must be positive.");
            }

            if (name == null || name.isBlank()) {
                throw new IllegalStateException(
                        "Product name is required.");
            }

            if (description == null) {
                description = "";
            }

            Objects.requireNonNull(
                    dateOfManufacture,
                    "Date of manufacture is required.");

            if (price < 0) {
                throw new IllegalStateException(
                        "Product price cannot be negative.");
            }

            if (category == null || category.isBlank()) {
                category = "no-category";
            }
        }
    }
}