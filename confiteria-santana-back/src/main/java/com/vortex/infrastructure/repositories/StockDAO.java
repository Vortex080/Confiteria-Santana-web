package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.Stock;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type Stock dao.
 */
@ApplicationScoped
@Transactional
public class StockDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persist.
     *
     * @param stock the stock
     */
    public void persist(Stock stock) {
        em.persist(stock);
    }

    /**
     * Find stock.
     *
     * @param id the id
     * @return the stock
     */
    public Stock find(Long id) {
        return em.find(Stock.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Stock> findAll() {
        return em.createQuery("select u from Stock u", Stock.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param stock the stock
     */
    public void delete(Stock stock) {
        em.remove(em.merge(stock));
    }

    /**
     * Update.
     *
     * @param stock the stock
     */
    public void update(Stock stock) {
        em.merge(stock);
    }

}
