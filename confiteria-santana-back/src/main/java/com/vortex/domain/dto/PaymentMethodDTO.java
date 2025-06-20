package com.vortex.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Auto-generated Javadoc
/**
 * The type Payment method dto.
 */
public class PaymentMethodDTO {

	private Long id;

	/** The user id. */
	private Long userId;

	/** The provider. */
	private String provider;

	/** The token. */
	private String token;

	/** The type. */
	private String type;

	/** The last 4. */
	private String last4;

	/** The expiry month. */
	private int expiryMonth;

	/** The expiry year. */
	private int expiryYear;

	/** The brand. */
	private String brand;

	/**
	 * Instantiates a new Payment method dto.
	 */
	public PaymentMethodDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets user.
	 *
	 * @return the user
	 */
	@JsonProperty("user")
	public Long getUser() {
		return userId;
	}

	/**
	 * Sets user.
	 *
	 * @param user the user
	 */
	@JsonProperty("user")
	public void setUser(Long user) {
		this.userId = user;
	}

	/**
	 * Gets provider.
	 *
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * Sets provider.
	 *
	 * @param provider the provider
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * Gets token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets token.
	 *
	 * @param token the token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets last 4.
	 *
	 * @return the last 4
	 */
	public String getLast4() {
		return last4;
	}

	/**
	 * Sets last 4.
	 *
	 * @param last4 the last 4
	 */
	public void setLast4(String last4) {
		this.last4 = last4;
	}

	/**
	 * Gets expiry month.
	 *
	 * @return the expiry month
	 */
	public int getExpiryMonth() {
		return expiryMonth;
	}

	/**
	 * Sets expiry month.
	 *
	 * @param expiryMonth the expiry month
	 */
	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	/**
	 * Gets expiry year.
	 *
	 * @return the expiry year
	 */
	public int getExpiryYear() {
		return expiryYear;
	}

	/**
	 * Sets expiry year.
	 *
	 * @param expiryYear the expiry year
	 */
	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}

	/**
	 * Gets the brand.
	 *
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Sets the brand.
	 *
	 * @param brand the new brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

}
