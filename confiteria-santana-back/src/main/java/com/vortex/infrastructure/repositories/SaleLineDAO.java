package com.vortex.infrastructure.repositories;

import java.util.List;

import com.vortex.domain.dto.SaleLineDTO;
import com.vortex.domain.entities.Product;
import com.vortex.domain.entities.SaleLine;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

// TODO: Auto-generated Javadoc
/**
 * The Class SaleLineDAO.
 */
@ApplicationScoped
@Transactional
public class SaleLineDAO {

	/** The em. */
	@PersistenceContext
	private EntityManager em;
	
	/** The dao. */
	private ProductDAO dao;

	/**
	 * Persist.
	 *
	 * @param sale the sale
	 */
	public void persist(SaleLine sale) {
		em.persist(sale);
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the sale line
	 */
	public SaleLine findById(Long id) {
		return em.find(SaleLine.class, id);
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<SaleLine> findAll() {
		return em.createQuery("select u from SaleLine u", SaleLine.class).getResultList();
	}

	/**
	 * Update.
	 *
	 * @param line the line
	 */
	public void update(SaleLine line) {
		em.merge(line);
	}

	/**
	 * Delete.
	 *
	 * @param line the line
	 */
	public void delete(SaleLine line) {
        SaleLine managed = em.merge(line);
        em.remove(managed);
	}

	/**
	 * Find by fields.
	 *
	 * @param dto the dto
	 * @return the sale line
	 */
	public SaleLine findByFields(SaleLineDTO dto) {

		Product product = dao.findById(dto.getProduct().getId());

		String jpql = "SELECT s FROM SaleLine s WHERE s.product"
				+ "AND s.cuantity = :cuantity AND s.price = :price AND s.subtotal = :subtotal";

		TypedQuery<SaleLine> query = em.createQuery(jpql, SaleLine.class)
				.setParameter("product", product)
				.setParameter("cuantity", dto.getCuantity()).setParameter("price", dto.getPrice())
				.setParameter("subtotal", dto.getSubtotal());

		return query.getResultStream().findFirst().orElse(null);
	}

}
