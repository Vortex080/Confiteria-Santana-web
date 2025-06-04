package com.vortex.domain.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The type Order.
 */
@Entity
@Table(name = "orders") // Usamos plural y en minúscula para seguir la convención
public class Order {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The user. */
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	/** The total. */
	@Column(nullable = false)
	private Long total;

	/** The shipping. */
	@Column(name = "shipping_cost", nullable = false)
	private Long shipping;

	/** The payment method. */
	@ManyToOne
	@JoinColumn(name = "payment_method_id", nullable = false)
	private PaymentMethod paymentMethod;

	/** The shipping address. */
	@ManyToOne
	@JoinColumn(name = "shipping_address_id", nullable = false)
	private Address shippingAddress;

	/** The billing address. */
	@ManyToOne
	@JoinColumn(name = "billing_address_id", nullable = false)
	private Address billingAddress;

	/** The sale. */
	@OneToOne
	@JoinColumn(name = "sale_id", referencedColumnName = "id", nullable = false)
	private Sale sale;

	/** The created at. */
	@Column(nullable = false)
	private LocalDateTime created_at;

	/** The updated at. */
	private LocalDateTime updated_at;

	/**
	 * Instantiates a new Order.
	 */
	public Order() {
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
	 * Gets user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets user.
	 *
	 * @param user the user
	 */
	public void setUser(User user) {
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
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets payment method.
	 *
	 * @param paymentMethod the payment method
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Gets shipping address.
	 *
	 * @return the shipping address
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * Sets shipping address.
	 *
	 * @param shippingAddress the shipping address
	 */
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * Gets billing address.
	 *
	 * @return the billing address
	 */
	public Address getBillingAddress() {
		return billingAddress;
	}

	/**
	 * Sets billing address.
	 *
	 * @param billingAddress the billing address
	 */
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * Gets created at.
	 *
	 * @return the created at
	 */
	public LocalDateTime getCreated_at() {
		return created_at;
	}

	/**
	 * Sets created at.
	 *
	 * @param created_at the created at
	 */
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	/**
	 * Gets updated at.
	 *
	 * @return the updated at
	 */
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	/**
	 * Sets updated at.
	 *
	 * @param updated_at the updated at
	 */
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

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

}
