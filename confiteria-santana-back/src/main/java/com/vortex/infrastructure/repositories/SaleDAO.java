
package com.vortex.infrastructure.repositories;

import java.util.List;

import com.vortex.domain.entities.Address;
import com.vortex.domain.entities.Sale;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

// TODO: Auto-generated Javadoc
/**
 * The Class SaleDAO.
 */
@ApplicationScoped
@Transactional
public class SaleDAO {

	/** The em. */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Persist.
	 *
	 * @param sale the sale
	 */
	public void persist(Sale sale) {
		em.persist(sale);
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the sale
	 */
	public Sale findById(Long id) {
		return em.find(Sale.class, id);
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Sale> findAll() {
		return em.createQuery("select u from Sale u", Sale.class).getResultList();
	}

	/**
	 * Update.
	 *
	 * @param sale the sale
	 */
	public void update(Sale sale) {
		em.merge(sale);
	}

	/**
	 * Delete.
	 *
	 * @param sale the sale
	 */
	public void delete(Sale sale) {
    	Sale managed = em.merge(sale);
    	em.remove(managed);
	}
}
