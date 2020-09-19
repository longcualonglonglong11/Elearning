package com.myclass.controller.admin;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.dialect.pagination.SQL2008StandardLimitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myclass.dto.CategoryDto;
import com.myclass.entity.Category;
import com.myclass.service.CategoryService;
@Controller
@RequestMapping("admin/category")
public class AdminCategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("categories", categoryService.findAllDto());
		return "admin/category/index";
	}

	@GetMapping("add")
	public String add(ModelMap modelMap) {
		modelMap.addAttribute("category", new CategoryDto());
		return "admin/category/add";
	}

	@PostMapping("add")
	public String add(ModelMap modelMap, @Valid @ModelAttribute("category") CategoryDto dto, BindingResult error) {
		if (error.hasErrors()) {
			return "admin/category/add";
		}
		if (categoryService.addDto(dto))
			return "redirect:/admin/category";
		modelMap.addAttribute("message", "Can't insert ... !");
		return "admin/category/add";
	}

	@GetMapping("edit/{id}")
	public String edit(Model modelMap, @PathVariable("id") int id) {
		CategoryDto dto = categoryService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute("category", dto);
			return "admin/category/edit";
		}
		modelMap.addAttribute("message", "A category not exists !");
		return "redirect:/admin/category";

	}

	@PostMapping("edit")
	public String edit(Model modelMap, @Valid @ModelAttribute("category") CategoryDto dto, BindingResult error) {
		if (error.hasErrors()) {
			return "admin/category/edit";
		}
		if (categoryService.updateDto(dto)) {
			return "redirect:/admin/category";
		}
		modelMap.addAttribute("message", "Can't update ... !");
		return "admin/category/edit";

	}

	@GetMapping("delete/{id}")
	public String delete(Model modelMap, @PathVariable("id") int id) {
		if (!categoryService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/admin/category";

	}

}
