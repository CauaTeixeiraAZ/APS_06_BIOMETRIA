package com.unip.biometria.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.unip.biometria.model.entities.Users;
import com.unip.biometria.utils.JpaUtils;

public class DaoUsers implements IDaoUsers {

	@Override
	public long save(Users user) {
		EntityManager entityManager = JpaUtils.getEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();;
		
		entityManager.close();
		return user.getId();
	}

	@Override
	public Users find(String email) {
		EntityManager entityManager = JpaUtils.getEntityManager();
		
		return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", Users.class)
                .setParameter("email", email)
                .getSingleResult();
	}
	
	
	public boolean findByEmail(String email) {
		EntityManager entityManager = JpaUtils.getEntityManager();
		
		email = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", Users.class)
                .setParameter("email", email)
                .getSingleResult().getEmail();
		return (email != null) ? true : false;
	}
	

	@Override
	public List<Users> findAll() {
		EntityManager entityManager = JpaUtils.getEntityManager();
		List<Users> users = entityManager.createQuery("from Users", Users.class).getResultList();
		
		entityManager.close();
		
		return users;
	
	}

	@Override
	public boolean update(Users user) {
		EntityManager entityManager = JpaUtils.getEntityManager();

		entityManager.getTransaction().begin();
		if(user != null) {
			entityManager.merge(user);		
		}
		entityManager.getTransaction().commit();

		entityManager.close();
		return true;
	}

	@Override
	public boolean delete(Users user) {
		EntityManager entityManager = JpaUtils.getEntityManager();

		entityManager.getTransaction().begin();
		if(user != null) {
			entityManager.remove(user);			
		}
		entityManager.getTransaction().commit();

		entityManager.close();
		return true;
	}

}
