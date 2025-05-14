package com.vortex.domain.dto;

import com.vortex.domain.enums.MovementReason;
import com.vortex.domain.enums.MovementType;

/**
 * The type Stock movements dto.
 */
public class StockMovementsDTO {

    private Long product;

    private MovementType type;

    private String unit;

    private MovementReason reason;

    /**
     * Instantiates a new Stock movements dto.
     */
    public StockMovementsDTO() {}

    /**
     * Gets product.
     *
     * @return the product
     */
    public Long getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(Long product) {
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
}
