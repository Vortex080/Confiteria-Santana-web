package com.vortex.domain.dto;

/**
 * The type Product photo dto.
 */
public class ProductPhotoDTO {

    private ProductDTO product;

    private String url;

    private String altText;

    /**
     * Instantiates a new Product photo dto.
     */
    public ProductPhotoDTO() {}

    /**
     * Gets product.
     *
     * @return the product
     */
    public ProductDTO getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(ProductDTO product) {
        this.product = product;
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
}
