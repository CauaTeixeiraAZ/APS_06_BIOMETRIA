package com.unip.biometria.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.unip.biometria.model.entities.Users;
import com.unip.biometria.model.utils.JpaUtils;

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
