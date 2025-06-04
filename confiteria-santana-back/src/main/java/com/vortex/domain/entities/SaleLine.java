package com.vortex.domain.entities;

import java.math.BigDecimal;

import com.vortex.domain.dto.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * The Class SaleLine.
 */
@Entity
@Table(name = "SaleLine")
public class SaleLine {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The product. */
	private ProductDTO  product;

	/** The cuantity. */
	@Column(nullable = false)
	private Long cuantity;

	/** The price. */
	@Column(nullable = false)
	private BigDecimal price;

	/** The subtotal. */
	@Column(nullable = false)
	private BigDecimal subtotal;

	/** The sale. */
	@ManyToOne
	@JoinColumn(name = "sale_id", nullable = false)
	private Sale sale;

	/**
	 * Gets the sale.
	 *
	 * @return the sale
	 */
	public Sale getSale() {
		return sale;
	}

	/**
	 * Sets the sale.
	 *
	 * @param sale the new sale
	 */
	public void setSale(Sale sale) {
		this.sale = sale;
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
