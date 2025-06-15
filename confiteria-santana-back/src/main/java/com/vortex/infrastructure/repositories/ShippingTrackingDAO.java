package com.vortex.infrastructure.repositories;


import com.vortex.domain.entities.ShippingTracking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type Shipping tracking dao.
 */
@ApplicationScoped
@Transactional
public class ShippingTrackingDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persist.
     *
     * @param shippingTracking the shipping tracking
     */
    public void persist(ShippingTracking shippingTracking) {
        em.persist(shippingTracking);
    }

    /**
     * Find shipping tracking.
     *
     * @param id the id
     * @return the shipping tracking
     */
    public ShippingTracking find(Long id) {
        return em.find(ShippingTracking.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<ShippingTracking> findAll() {
        return em.createQuery("select u from ShippingTracking u", ShippingTracking.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param shippingTracking the shipping tracking
     */
    public void delete(ShippingTracking shippingTracking) {
        em.remove(shippingTracking);
    }

    /**
     * Update.
     *
     * @param shippingTracking the shipping tracking
     */
    public void update(ShippingTracking shippingTracking) {
        em.merge(shippingTracking);
    }

}
