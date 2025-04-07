package com.vortex.domain.dto;

/**
 * The type Stock dto.
 */
public class StockDTO {

    private ProductDTO product;

    private int quantity;

    /**
     * Instantiates a new Stock dto.
     */
    public StockDTO() {}

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
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
