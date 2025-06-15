package com.vortex.domain.dto;

import com.vortex.domain.entities.Alergens;
import com.vortex.domain.entities.ProductPhoto;
import com.vortex.infrastructure.repositories.AlergensDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductDTO.
 */
public class ProductDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The name. */
	private String name;

	/** The description. */
	private String description;

	/** The price. */
	private int price;

	/** The unit. */
	private String unit;

	/** The alergens. */
	private ArrayList<AlergenDTO> alergens;

	/** The category. */
	private CategoryDTO category;

	/** The photos. */
	private List<ProductPhotoDTO> photos;

	/**
	 * Instantiates a new Product dto.
	 */
	public ProductDTO() {
	}

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
	public ArrayList<AlergenDTO> getAlergens() {
		return alergens;
	}

	/**
	 * Sets alergens.
	 *
	 * @param alergens the alergens
	 */
	public void setAlergens(ArrayList<AlergenDTO> alergens) {
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
	public List<ProductPhotoDTO> getPhotos() {
		return photos;
	}

	/**
	 * Sets photos.
	 *
	 * @param photos the photos
	 */
	public void setPhotos(List<ProductPhotoDTO> photos) {
		this.photos = photos;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
