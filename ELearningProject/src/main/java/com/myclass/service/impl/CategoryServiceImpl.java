package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.myclass.dto.CategoryDto;
import com.myclass.entity.Category;
import com.myclass.repository.CategoryRepository;
import com.myclass.service.CategoryService;

@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, Integer> implements CategoryService{
	@Autowired
	CategoryRepository categoryRepository;
	public List<CategoryDto> findAllDto() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> dtos = new ArrayList<CategoryDto>();
		for (Category category : categories) {
			CategoryDto dto = new CategoryDto();
			dto.setId(category.getId());
			dto.setIcon(category.getIcon());
			dto.setTitle(category.getTitle());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public boolean addDto(CategoryDto dto) {
		try {
			Category category = new Category();
			category.setTitle(dto.getTitle());
			category.setIcon(dto.getIcon());
			categoryRepository.save(category);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDto(CategoryDto dto) {
		try {
			Category category = new Category();
			category.setId(dto.getId());
			category.setTitle(dto.getTitle());
			category.setIcon(dto.getIcon());
			categoryRepository.saveAndFlush(category);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CategoryDto findDtoById(int id) {
		Optional<Category> category = categoryRepository.findById(id);
		CategoryDto dto = new CategoryDto();
		dto.setId(id);
		dto.setTitle(category.get().getTitle());
		dto.setIcon(category.get().getIcon());
		return dto;
	}
}
