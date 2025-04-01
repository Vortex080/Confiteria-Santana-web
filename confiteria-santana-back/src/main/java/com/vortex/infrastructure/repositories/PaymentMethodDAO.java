package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.PaymentMethod;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type Payment method dao.
 */
@ApplicationScoped
@Transactional
public class PaymentMethodDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persist.
     *
     * @param paymentMethod the payment method
     */
    public void persist(PaymentMethod paymentMethod) {
        em.persist(paymentMethod);
    }

    /**
     * Find payment method.
     *
     * @param id the id
     * @return the payment method
     */
    public PaymentMethod find(int id) {
        return em.find(PaymentMethod.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<PaymentMethod> findAll() {
        return em.createQuery("select u from PaymentMethod u", PaymentMethod.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param paymentMethod the payment method
     */
    public void delete(PaymentMethod paymentMethod) {
        em.remove(paymentMethod);
    }

    /**
     * Update.
     *
     * @param paymentMethod the payment method
     */
    public void update(PaymentMethod paymentMethod) {
        em.merge(paymentMethod);
    }

}
