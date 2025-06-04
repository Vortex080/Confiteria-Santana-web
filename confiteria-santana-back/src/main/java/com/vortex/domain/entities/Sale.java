package com.vortex.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Sale.
 */
@Entity
@Table(name = "Sale")
public class Sale {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The date. */
	@Column(nullable = false)
	private LocalDateTime date;

	/** The total. */
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal total;

	/** The metodo pago. */
	@Column(nullable = false)
	private String metodoPago;

	/** The line. */
	@OneToMany(mappedBy = "sale", cascade = CascadeType.PERSIST)
	private List<SaleLine> line;

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
	 * Gets the lineas.
	 *
	 * @return the lineas
	 */
	public List<SaleLine> getLineas() {
		return line;
	}

	/**
	 * Sets the lineas.
	 *
	 * @param lineas the new lineas
	 */
	public void setLineas(List<SaleLine> lineas) {
		this.line = lineas;
	}
}
