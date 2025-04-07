package com.vortex.domain.dto;

/**
 * The type Order dto.
 */
public class OrderDTO {

    private UserDTO user;

    private Long total;

    private Long shipping;

    private PaymentMethodDTO paymentMethod;

    private AddressDTO billingAddress;

    /**
     * Instantiates a new Order dto.
     */
    public OrderDTO() {}

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
    public PaymentMethodDTO getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets payment method.
     *
     * @param paymentMethod the payment method
     */
    public void setPaymentMethod(PaymentMethodDTO paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets billing address.
     *
     * @return the billing address
     */
    public AddressDTO getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets billing address.
     *
     * @param billingAddress the billing address
     */
    public void setBillingAddress(AddressDTO billingAddress) {
        this.billingAddress = billingAddress;
    }
}
