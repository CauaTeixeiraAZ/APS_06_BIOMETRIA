package com.unip.biometria.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.unip.biometria.model.entities.Company;
import com.unip.biometria.utils.JpaUtils;

public class DaoCompany implements IDaoCompany {

	@Override
	public long save(Company company) {
		EntityManager entityManager = JpaUtils.getEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(company);
		entityManager.getTransaction().commit();

		entityManager.close();
		return company.getId();
	}

	@Override
	public Company find(int cnpj) {
		EntityManager entityManager = JpaUtils.getEntityManager();

		return entityManager.createQuery("SELECT u FROM Company u WHERE u.cnpj = :cnpj", Company.class)
				.setParameter("cnpj", cnpj).getSingleResult();
	}

	@Override
	public Company find(String name) {
		EntityManager entityManager = JpaUtils.getEntityManager();

		return entityManager.createQuery("SELECT u FROM Company u WHERE u.name = :name", Company.class)
				.setParameter("name", name).getSingleResult();
	}

	@Override
	public List<Company> findAll() {
		EntityManager entityManager = JpaUtils.getEntityManager();
		List<Company> company = entityManager.createQuery("from Company", Company.class).getResultList();

		entityManager.close();

		return company;
	}

	@Override
	public boolean update(Company company) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Company company) {
		// TODO Auto-generated method stub
		return false;
	}

}
