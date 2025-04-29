package com.vortex.infrastructure.repositories;

import com.vortex.domain.dto.AddressDTO;
import com.vortex.domain.dto.OrderDTO;
import com.vortex.domain.entities.Address;
import com.vortex.domain.entities.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type Order dao.
 */
@ApplicationScoped
@Transactional
public class OrderDAO {

    @PersistenceContext
    private EntityManager em;


    /**
     * Persist.
     *
     * @param order the order
     */
    public void persist(Order order) {
        em.persist(order);
    }

    /**
     * Find order.
     *
     * @param id the id
     * @return the order
     */
    public Order find(Long id) {
        return em.find(Order.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param order the order
     */
    public void delete(Order order) {
        em.remove(order);
    }

    /**
     * Update.
     *
     * @param order the order
     */
    public void update(Order order) {
        em.merge(order);
    }

    public Order findByFields(OrderDTO orderDTO) {

        String jpql = "SELECT o FROM Order o WHERE o.user = :user " +
                "AND o.total = :total AND o.shipping = :shipping " +
                "AND o.paymentMethod = :paymentMethod AND o.billingAddress = :billingAddress";

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setParameter("user", orderDTO.getUser())
                .setParameter("total", orderDTO.getTotal())
                .setParameter("shipping", orderDTO.getShipping())
                .setParameter("paymentMethod", orderDTO.getPaymentMethod())
                .setParameter("billingAddress", orderDTO.getBillingAddress());

        return query.getResultStream().findFirst().orElse(null);
    }


}
