package com.vortex.infrastructure.repositories;

import java.util.List;

import com.vortex.domain.entities.ProductPhoto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * The type Product photo dao.
 */
@ApplicationScoped
@Transactional
public class ProductPhotoDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Persist.
	 *
	 * @param product the product
	 */
	public void persist(ProductPhoto product) {
		em.persist(product);
	}

	/**
	 * Find product photo.
	 *
	 * @param id the id
	 * @return the product photo
	 */
	public ProductPhoto find(Long id) {
		return em.find(ProductPhoto.class, id);
	}

	public ProductPhoto findExistingPhoto(ProductPhoto photo) {
	    try {
	        return em.createQuery(
	            "SELECT ph FROM ProductPhoto ph " +
	            "WHERE ph.url = :url", ProductPhoto.class
	        )
	        .setParameter("url", photo.getUrl())
	        .getSingleResult();
	    } catch (NoResultException e) {
	        return null; // No se encontró ninguna coincidencia
	    }
	}


	/**
	 * Delete.
	 *
	 * @param product the product
	 */
	public void delete(ProductPhoto product) {
		em.remove(product);
	}

	/**
	 * Update.
	 *
	 * @param product the product
	 */
	public void update(ProductPhoto product) {
		em.merge(product);
	}

	public List<ProductPhoto> findByProduct(Long productId) {
		return em.createQuery("SELECT u FROM ProductPhoto u WHERE u.product.id = :productId", ProductPhoto.class)
				.setParameter("productId", productId).getResultList();
	}

}
