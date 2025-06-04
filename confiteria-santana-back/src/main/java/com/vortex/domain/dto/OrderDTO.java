package com.vortex.domain.dto;

import java.time.LocalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The type Order dto.
 */
public class OrderDTO {

	/** The id. */
	private Long id;

	/** The user. */
	private Long user;

	/** The total. */
	private Long total;

	/** The shipping. */
	private Long shipping;

	/** The payment method. */
	private Long paymentMethod;

	/** The billing address. */
	private Long billingAddress;

	/** The sale. */
	private Long sale;

	/** The created at. */
	private LocalDateTime created_at;

	/**
	 * Instantiates a new Order dto.
	 */
	public OrderDTO() {
	}

	/**
	 * Gets user.
	 *
	 * @return the user
	 */
	public Long getUser() {
		return user;
	}

	/**
	 * Sets user.
	 *
	 * @param user the user
	 */
	public void setUser(Long user) {
		this.user = user;
	}

	/**
	 * Gets total.
	 *
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * Sets total.
	 *
	 * @param total the total
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * Gets shipping.
	 *
	 * @return the shipping
	 */
	public Long getShipping() {
		return shipping;
	}

	/**
	 * Sets shipping.
	 *
	 * @param shipping the shipping
	 */
	public void setShipping(Long shipping) {
		this.shipping = shipping;
	}

	/**
	 * Gets payment method.
	 *
	 * @return the payment method
	 */
	public Long getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets payment method.
	 *
	 * @param paymentMethod the payment method
	 */
	public void setPaymentMethod(Long paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Gets billing address.
	 *
	 * @return the billing address
	 */
	public Long getBillingAddress() {
		return billingAddress;
	}

	/**
	 * Sets billing address.
	 *
	 * @param billingAddress the billing address
	 */
	public void setBillingAddress(Long billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * Gets the sale.
	 *
	 * @return the sale
	 */
	public Long getSale() {
		return sale;
	}

	/**
	 * Sets the sale.
	 *
	 * @param sale the new sale
	 */
	public void setSale(Long sale) {
		this.sale = sale;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public LocalDateTime getCreated_at() {
		return created_at;
	}

	/**
	 * Sets the created at.
	 *
	 * @param created_at the new created at
	 */
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
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
