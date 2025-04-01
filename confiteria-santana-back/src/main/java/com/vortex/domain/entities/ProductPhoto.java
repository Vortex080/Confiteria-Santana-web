package com.vortex.domain.entities;

import jakarta.persistence.*;

/**
 * The type Product photo.
 */
@Entity
@Table(name = "product_photos")
public class ProductPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String alt_text;

    /**
     * Instantiates a new Product photo.
     */
    public ProductPhoto() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
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
    public String getAlt_text() {
        return alt_text;
    }

    /**
     * Sets alt text.
     *
     * @param alt_text the alt text
     */
    public void setAlt_text(String alt_text) {
        this.alt_text = alt_text;
    }
}
