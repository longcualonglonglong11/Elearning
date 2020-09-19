package com.myclass.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myclass.service.GenericService;

public class GenericServiceImpl<T, K extends Serializable> implements GenericService<T, K> {

	@Autowired
	private JpaRepository<T, K> genericRepository;

	@Override
	public List<T> findAll() {
		return genericRepository.findAll();
	}

	@Override
	public Optional<T> findById(K id) {
		return genericRepository.findById(id);
	}

	@Override
	public boolean add(T entity) {
		try {
			genericRepository.save(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	@Override
	public boolean update(T entity) {
		try {
			genericRepository.saveAndFlush(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	@Override
	public boolean deleteById(K id) {
		try {
			genericRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


}
