package com.myclass.service;

import java.util.List;

import com.myclass.dto.CategoryDto;
import com.myclass.dto.RoleDto;
import com.myclass.entity.Category;

public interface CategoryService extends GenericService<Category, Integer> {
	List<CategoryDto> findAllDto();

	boolean addDto(CategoryDto dto);

	boolean updateDto(CategoryDto dto);

	CategoryDto findDtoById(int id);
}
