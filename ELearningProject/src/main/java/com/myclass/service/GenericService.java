package com.myclass.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, K> {
	List<T> findAll();
	Optional<T> findById(K id); 
	boolean add(T entity);
	boolean update(T entity);
	boolean deleteById(K id);
}
