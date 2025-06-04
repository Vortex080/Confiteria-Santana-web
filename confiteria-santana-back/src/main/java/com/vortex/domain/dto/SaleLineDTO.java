package com.vortex.domain.dto;

import java.math.BigDecimal;

/**
 * The Class SaleLineDTO.
 */
public class SaleLineDTO {

	/** The product. */
	private ProductDTO product;

	/** The cuantity. */
	private Long cuantity;

	/** The price. */
	private BigDecimal price;

	/** The subtotal. */
	private BigDecimal subtotal;

	/**
	 * Gets the product.
	 *
	 * @return the product
	 */
	public ProductDTO getProduct() {
		return product;
	}

	/**
	 * Sets the product.
	 *
	 * @param product the new product
	 */
	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	/**
	 * Gets the cuantity.
	 *
	 * @return the cuantity
	 */
	public Long getCuantity() {
		return cuantity;
	}

	/**
	 * Sets the cuantity.
	 *
	 * @param cuantity the new cuantity
	 */
	public void setCuantity(Long cuantity) {
		this.cuantity = cuantity;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * Gets the subtotal.
	 *
	 * @return the subtotal
	 */
	public BigDecimal getSubtotal() {
		return subtotal;
	}

	/**
	 * Sets the subtotal.
	 *
	 * @param subtotal the new subtotal
	 */
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

}
