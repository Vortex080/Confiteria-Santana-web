package com.vortex.domain.dto;

import com.vortex.infrastructure.repositories.UserDAO;

/**
 * The type Payments dto.
 */
public class PaymentsDTO {

    private UserDTO user;

    private OrderDTO order;

    private Long paymentMethod;

    private String provider;

    private String status;

    private String currency;

    /**
     * Instantiates a new Payments dto.
     */
    public PaymentsDTO() {}

    /**
     * Gets user.
     *
     * @return the user
     */
    public UserDTO getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(UserDTO user) {
        this.user = user;
    }

    /**
     * Gets order.
     *
     * @return the order
     */
    public OrderDTO getOrder() {
        return order;
    }

    /**
     * Sets order.
     *
     * @param order the order
     */
    public void setOrder(OrderDTO order) {
        this.order = order;
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
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
