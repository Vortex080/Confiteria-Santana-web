package com.vortex.domain.dto;

import java.util.List;

/**
 * The type Category dto.
 */
public class CategoryDTO {

    private String name;

    private String description;

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
}
