package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type User dao.
 */
@ApplicationScoped
@Transactional
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persist.
     *
     * @param user the user
     */
    public void persist(User user) {
        em.persist(user);
    }

    /**
     * Find user.
     *
     * @param id the id
     * @return the user
     */
    public User find(int id) {
        return em.find(User.class, id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(int id) {
        em.remove(em.find(User.class, id));
    }

    /**
     * Delete.
     *
     * @param user the user
     */
    public void delete(User user) {
        em.remove(em.merge(user));
    }

    // TODO - AÑADIR FIND BY EMAIL Y FIND BY USER

}
