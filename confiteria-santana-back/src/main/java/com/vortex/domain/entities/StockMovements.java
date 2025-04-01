package com.vortex.domain.entities;

import com.vortex.domain.enums.MovementReason;
import com.vortex.domain.enums.MovementType;
import jakarta.persistence.*;

import java.sql.Time;

/**
 * The type Stock movements.
 */
@Entity
@Table(name = "StockMovements")
public class StockMovements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private MovementType type;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private MovementReason reason;

    private Time created_at;

    /**
     * Instantiates a new Stock movements.
     */
    public StockMovements() {
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
     * Gets product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public MovementType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(MovementType type) {
        this.type = type;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets reason.
     *
     * @return the reason
     */
    public MovementReason getReason() {
        return reason;
    }

    /**
     * Sets reason.
     *
     * @param reason the reason
     */
    public void setReason(MovementReason reason) {
        this.reason = reason;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Time getCreated_at() {
        return created_at;
    }

    /**
     * Sets created at.
     *
     * @param created_at the created at
     */
    public void setCreated_at(Time created_at) {
        this.created_at = created_at;
    }
}
