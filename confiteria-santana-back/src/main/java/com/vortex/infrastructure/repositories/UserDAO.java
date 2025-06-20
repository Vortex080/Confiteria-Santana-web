package com.vortex.infrastructure.repositories;

import com.vortex.domain.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * The type User dao.
 */
@ApplicationScoped
@Transactional
public class UserDAO {

	/** The em. */
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
	public User find(Long id) {
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

	public void update(User user) {
		em.merge(user);
	}
	
	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the user
	 */
	public User findByEmail(String email) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
		query.setParameter("email", email);
		List<User> users = query.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the user
	 */
	public User findByUsername(String username) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
		query.setParameter("username", username);
		List<User> users = query.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

	/**
	 * Verify user.
	 *
	 * @param email the email
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean verifyUser(String email, String password) {
		try {
			User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
					.setParameter("email", email).getSingleResult();
			return user.getPassword().equals(password);
		} catch (NoResultException e) {
			return false;
		}
	}

}
