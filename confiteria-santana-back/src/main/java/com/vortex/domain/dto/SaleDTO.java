package com.vortex.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class SaleDTO.
 */
public class SaleDTO {

	/** The id. */
	private Long id;

	/** The date. */
	private LocalDateTime date;

	/** The total. */
	private BigDecimal total;

	/** The metodo pago. */
	private String metodoPago;

	/** The line. */
	private List<SaleLineDTO> line;

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * Gets the metodo pago.
	 *
	 * @return the metodo pago
	 */
	public String getMetodoPago() {
		return metodoPago;
	}

	/**
	 * Sets the metodo pago.
	 *
	 * @param metodoPago the new metodo pago
	 */
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	/**
	 * Gets the line.
	 *
	 * @return the line
	 */
	public List<SaleLineDTO> getLine() {
		return line;
	}

	/**
	 * Sets the line.
	 *
	 * @param line the new line
	 */
	public void setLine(List<SaleLineDTO> line) {
		this.line = line;
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