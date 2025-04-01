package com.vortex.infrastructure.repositories;


import com.vortex.domain.entities.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type Category dao.
 */
@ApplicationScoped
@Transactional
public class CategoryDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persist.
     *
     * @param category the category
     */
    public void persist(Category category) {
        em.persist(category);
    }

    /**
     * Find category.
     *
     * @param id the id
     * @return the category
     */
    public Category find(int id) {
        return em.find(Category.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param category the category
     */
    public void delete(Category category) {
        em.remove(category);
    }

    /**
     * Update category.
     *
     * @param category the category
     * @return the category
     */
    public Category update(Category category) {
        return em.merge(category);
    }

}
