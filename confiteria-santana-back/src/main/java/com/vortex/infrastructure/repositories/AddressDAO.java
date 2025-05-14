package com.vortex.infrastructure.repositories;

import com.vortex.domain.dto.AddressDTO;
import com.vortex.domain.entities.Address;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;


/**
 * The type Address dao.
 */
@ApplicationScoped
@Transactional
public class AddressDAO {

    /** The em. */
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
     * Find by fields.
     *
     * @param addressDTO the address DTO
     * @return the address
     */
    public Address findByFields(AddressDTO addressDTO){

        String jpql = "SELECT a FROM Address a WHERE a.street = :street AND a.number = :number " +
                "AND a.city = :city AND a.state = :state AND a.country = :country AND a.postalCode = :postalCode ";

        if (addressDTO.getFlat() != null) {
            jpql += " AND a.flat = :flat";
        } else {
            jpql += " AND a.flat IS NULL";
        }

        if (addressDTO.getDoor() != null) {
            jpql += " AND a.door = :door";
        } else {
            jpql += " AND a.door IS NULL";
        }

        TypedQuery<Address> query = em.createQuery(jpql, Address.class)
                .setParameter("street", addressDTO.getStreet())
                .setParameter("number", addressDTO.getNumber())
                .setParameter("city", addressDTO.getCity())
                .setParameter("state", addressDTO.getState())
                .setParameter("country", addressDTO.getCountry())
                .setParameter("postalCode", addressDTO.getPostalCode());

        if (addressDTO.getFlat() != null) {
            query.setParameter("flat", addressDTO.getFlat());
        }
        if (addressDTO.getDoor() != null) {
            query.setParameter("door", addressDTO.getDoor());
        }

        return query.getResultStream().findFirst().orElse(null);

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
