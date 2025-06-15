package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.Alergens;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type Alergens.
 */
@ApplicationScoped
@Transactional
public class AlergensDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persist.
     *
     * @param alergens the alergens
     */
    public void persist(Alergens alergens) {
        em.persist(alergens);
    }

    /**
     * Find alergens.
     *
     * @param id the id
     * @return the alergens
     */
    public Alergens find(Long id) {
        return em.find(Alergens.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Alergens> findAll() {
        return em.createQuery("select u from Alergens u", Alergens.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param alergens the alergens
     */
    public void delete(Alergens alergens) {
        em.remove(em.merge(alergens));
    }

    /**
     * Update.
     *
     * @param alergens the alergens
     */
    public void update(Alergens alergens) {
    	Alergens managed = em.merge(alergens);
    	em.remove(managed);
    }

}
