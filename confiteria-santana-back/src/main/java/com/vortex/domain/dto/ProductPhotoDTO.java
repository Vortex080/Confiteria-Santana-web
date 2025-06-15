package com.vortex.domain.dto;

// TODO: Auto-generated Javadoc
/**
 * The type Product photo dto.
 */
public class ProductPhotoDTO {

	/** The id. */
	private Long id;

	/** The url. */
	private String url;

	/** The alt text. */
	private String altText;

	/**
	 * Instantiates a new Product photo dto.
	 */
	public ProductPhotoDTO() {
	}

	/**
	 * Gets url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets url.
	 *
	 * @param url the url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets alt text.
	 *
	 * @return the alt text
	 */
	public String getAltText() {
		return altText;
	}

	/**
	 * Sets alt text.
	 *
	 * @param altText the alt text
	 */
	public void setAltText(String altText) {
		this.altText = altText;
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
