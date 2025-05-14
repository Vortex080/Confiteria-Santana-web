package com.vortex.domain.dto;

/**
 * The type Payment method dto.
 */
public class PaymentMethodDTO {

    private Long userId;

    private String provider;

    private String token;

    private String type;

    private String last4;

    private int expiryMonth;

    private int expiryYear;

    /**
     * Instantiates a new Payment method dto.
     */
    public PaymentMethodDTO() {}

    /**
     * Gets user.
     *
     * @return the user
     */
    public Long getUser() {
        return userId;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
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
}
