package com.unip.biometria.dao;

import java.util.List;

import com.unip.biometria.model.entities.Company;

public interface IDaoCompany {
	long save(Company company);
	Company find(int cnpj);
	Company find(String name);
	List<Company> findAll();	
	boolean update(Company company);
	boolean delete(Company company);
}
