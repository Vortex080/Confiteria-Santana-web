package com.vortex.domain.dto;

/**
 * The type Shipping tracking.
 */
public class ShippingTracking {

    private OrderDTO order;

    private String carrier;

    private String trakingNumber;

    private String status;

    /**
     * Instantiates a new Shipping tracking.
     */
    public ShippingTracking() {}

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
     * Gets carrier.
     *
     * @return the carrier
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * Sets carrier.
     *
     * @param carrier the carrier
     */
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets traking number.
     *
     * @return the traking number
     */
    public String getTrakingNumber() {
        return trakingNumber;
    }

    /**
     * Sets traking number.
     *
     * @param trakingNumber the traking number
     */
    public void setTrakingNumber(String trakingNumber) {
        this.trakingNumber = trakingNumber;
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
}
