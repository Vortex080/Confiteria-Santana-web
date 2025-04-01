package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.PaymentMethod;
import com.vortex.domain.entities.Payments;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type Payments dao.
 */
@ApplicationScoped
@Transactional
public class PaymentsDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persist.
     *
     * @param payments the payments
     */
    public void persist(Payments payments) {
        em.persist(payments);
    }

    /**
     * Find payments.
     *
     * @param id the id
     * @return the payments
     */
    public Payments find(int id) {
        return em.find(Payments.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Payments> findAll() {
        return em.createQuery("select u from Payments u", Payments.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param payments the payments
     */
    public void delete(Payments payments) {
        em.remove(payments);
    }

    /**
     * Update.
     *
     * @param payments the payments
     */
    public void update(Payments payments) {
        em.merge(payments);
    }
}
