
package com.vortex.infrastructure.repositories;

import com.vortex.domain.dto.ProductDTO;
import com.vortex.domain.entities.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type Product dao.
 */
@ApplicationScoped
@Transactional
public class ProductDAO {

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
     * Find by id product.
     *
     * @param id the id
     * @return the product
     */
    public Product findById(Long id) {
        return em.find(Product.class, id);
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


    public Product findByFields(ProductDTO productDTO) {

        String jpql = "SELECT p FROM Product p WHERE p.name = :name " +
                "AND p.description = :description " +
                "AND p.price = :price AND p.unit = :unit";

        TypedQuery<Product> query = em.createQuery(jpql, Product.class)
                .setParameter("name", productDTO.getName())
                .setParameter("description", productDTO.getDescription())
                .setParameter("price", productDTO.getPrice())
                .setParameter("unit", productDTO.getUnit());

        return query.getResultStream().findFirst().orElse(null);
    }

}
