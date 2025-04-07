package com.vortex.domain.dto;

import com.vortex.domain.entities.Alergens;
import com.vortex.domain.entities.ProductPhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Product dto.
 */
public class ProductDTO {

    private String name;

    private String description;

    private int price;

    private  String unit;

    private ArrayList<Alergens> alergens;

    private CategoryDTO category;

    private List<ProductPhoto> photos;

    /**
     * Instantiates a new Product dto.
     */
    public ProductDTO() {}

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
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets alergens.
     *
     * @return the alergens
     */
    public ArrayList<Alergens> getAlergens() {
        return alergens;
    }

    /**
     * Sets alergens.
     *
     * @param alergens the alergens
     */
    public void setAlergens(ArrayList<Alergens> alergens) {
        this.alergens = alergens;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public CategoryDTO getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    /**
     * Gets photos.
     *
     * @return the photos
     */
    public List<ProductPhoto> getPhotos() {
        return photos;
    }

    /**
     * Sets photos.
     *
     * @param photos the photos
     */
    public void setPhotos(List<ProductPhoto> photos) {
        this.photos = photos;
    }
}
