package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.Address;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;


/**
 * The type Address dao.
 */
@ApplicationScoped
@Transactional
public class AddressDAO {

    @PersistenceContext
    private EntityManager em;


    /**
     * Persist.
     *
     * @param address the address
     */
    public void persist(Address address) {
        em.persist(address);
    }


    /**
     * Find by id address.
     *
     * @param id the id
     * @return the address
     */
    public Address findById(Long id) {
        return em.find(Address.class, id);
    }


    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Address> findAll() {
        return em.createQuery("select u from Address u", Address.class).getResultList();
    }


    /**
     * Update.
     *
     * @param address the address
     */
    public void update(Address address) {
        em.merge(address);
    }


    /**
     * Delete.
     *
     * @param address the address
     */
    public void delete(Address address) {
        em.remove(address);
    }

}
