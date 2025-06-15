package com.vortex.infrastructure.repositories;

import com.vortex.domain.dto.PaymentMethodDTO;
import com.vortex.domain.entities.PaymentMethod;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
    public PaymentMethod find(Long id) {
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


    public PaymentMethod findByFields(PaymentMethodDTO paymentMethodDTO) {

        String jpql = "SELECT p FROM PaymentMethod p WHERE p.user = :user " +
                "AND p.expiryMonth = :expiryMonth AND p.expiryYear = :expiryYear";

        if (paymentMethodDTO.getProvider() != null) {
            jpql += " AND p.provider = :provider";
        } else {
            jpql += " AND p.provider IS NULL";
        }

        if (paymentMethodDTO.getToken() != null) {
            jpql += " AND p.token = :token";
        } else {
            jpql += " AND p.token IS NULL";
        }

        if (paymentMethodDTO.getType() != null) {
            jpql += " AND p.type = :type";
        } else {
            jpql += " AND p.type IS NULL";
        }

        if (paymentMethodDTO.getLast4() != null) {
            jpql += " AND p.last4 = :last4";
        } else {
            jpql += " AND p.last4 IS NULL";
        }

        TypedQuery<PaymentMethod> query = em.createQuery(jpql, PaymentMethod.class)
                .setParameter("user", paymentMethodDTO.getUser())
                .setParameter("expiryMonth", paymentMethodDTO.getExpiryMonth())
                .setParameter("expiryYear", paymentMethodDTO.getExpiryYear());

        if (paymentMethodDTO.getProvider() != null) {
            query.setParameter("provider", paymentMethodDTO.getProvider());
        }
        if (paymentMethodDTO.getToken() != null) {
            query.setParameter("token", paymentMethodDTO.getToken());
        }
        if (paymentMethodDTO.getType() != null) {
            query.setParameter("type", paymentMethodDTO.getType());
        }
        if (paymentMethodDTO.getLast4() != null) {
            query.setParameter("last4", paymentMethodDTO.getLast4());
        }

        return query.getResultStream().findFirst().orElse(null);
    }


}
