package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.Product;
import com.vortex.domain.entities.ProductPhoto;
import com.vortex.domain.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

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
    public ProductPhoto find(int id) {
        return em.find(ProductPhoto.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Product> findAllWithEverything() {
        return em.createQuery(
            "SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.alergens " +
            "LEFT JOIN FETCH p.category " +
            "LEFT JOIN FETCH p.photos", Product.class
        ).getResultList();
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
                 .setParameter("productId", productId)
                 .getResultList();
    }

}
