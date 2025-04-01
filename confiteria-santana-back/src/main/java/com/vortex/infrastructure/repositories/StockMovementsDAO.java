package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.Stock;
import com.vortex.domain.entities.StockMovements;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type Stock movements dao.
 */
@ApplicationScoped
@Transactional
public class StockMovementsDAO {

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
     * Find stock movements.
     *
     * @param id the id
     * @return the stock movements
     */
    public StockMovements find(int id) {
        return em.find(StockMovements.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<StockMovements> findAll() {
        return em.createQuery("select u from StockMovements u", StockMovements.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param stockMovements the stock movements
     */
    public void delete(StockMovements stockMovements) {
        em.remove(stockMovements);
    }

    /**
     * Update.
     *
     * @param stockMovements the stock movements
     */
    public void update(StockMovements stockMovements) {
        em.merge(stockMovements);
    }
}
