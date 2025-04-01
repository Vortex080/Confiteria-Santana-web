package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.Product;
import com.vortex.domain.entities.ProductPhoto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    public void persist(Product product) {
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
    public List<ProductPhoto> findAll() {
        return em.createQuery("select p from ProductPhoto p", ProductPhoto.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param product the product
     */
    public void delete(Product product) {
        em.remove(em.merge(product));
    }

    /**
     * Update.
     *
     * @param product the product
     */
    public void update(Product product) {
        em.merge(product);
    }
}
