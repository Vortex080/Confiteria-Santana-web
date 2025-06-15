package com.vortex.domain.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * The type Shipping tracking.
 */
@Entity
@Table(name = "ShippingTracking")
public class ShippingTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String carrier;

    @Column(nullable = false)
    private String tracking_number;

    @Column(nullable = false)
    private String status;

    private Timestamp updated_at;

    /**
     * Instantiates a new Shipping tracking.
     */
    public ShippingTracking() {

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
     * Gets order.
     *
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets order.
     *
     * @param order the order
     */
    public void setOrder(Order order) {
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
     * Gets tracking number.
     *
     * @return the tracking number
     */
    public String getTracking_number() {
        return tracking_number;
    }

    /**
     * Sets tracking number.
     *
     * @param tracking_number the tracking number
     */
    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
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
     * Gets updated at.
     *
     * @return the updated at
     */
    public Timestamp getUpdated_at() {
        return updated_at;
    }

    /**
     * Sets updated at.
     *
     * @param updated_at the updated at
     */
    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
