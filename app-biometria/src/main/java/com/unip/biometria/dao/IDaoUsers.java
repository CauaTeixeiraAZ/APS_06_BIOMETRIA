package com.unip.biometria.dao;

import java.util.List;

import com.unip.biometria.model.entities.Users;

public interface IDaoUsers {
	long save(Users user);
	Users find(String email);
	boolean findByEmail(String email);
	List<Users> findAll();
	boolean update(Users user);
	boolean delete(Users user);
}
