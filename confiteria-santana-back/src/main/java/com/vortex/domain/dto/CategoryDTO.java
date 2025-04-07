package com.vortex.domain.dto;

import java.util.List;

/**
 * The type Category dto.
 */
public class CategoryDTO {

    private String name;

    private String description;

    private List<ProductDTO> products;

    /**
     * Instantiates a new Category dto.
     */
    public CategoryDTO() {}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get products list <>.
     *
     * @return the list <>
     */
    public List<ProductDTO> getProducts() {
        return products;
    }

    /**
     * Sets products.
     *
     * @param products the products
     */
    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
