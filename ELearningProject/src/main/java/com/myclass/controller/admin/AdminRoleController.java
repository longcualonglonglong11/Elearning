package com.myclass.controller.admin;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myclass.dto.RoleDto;
import com.myclass.entity.Role;
import com.myclass.service.CourseService;
import com.myclass.service.RoleService;
@Controller
@RequestMapping("admin/role")
public class AdminRoleController {
	@Autowired
	private RoleService roleService;
	

	@GetMapping("")
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("roles", roleService.findAllDto());
		return "admin/role/index";
	}

	@GetMapping("add")
	public String add(ModelMap modelMap) {
		modelMap.addAttribute("role", new RoleDto());
		return "admin/role/add";
	}

	@PostMapping("add")
	public String add(ModelMap modelMap, @Valid @ModelAttribute("role") RoleDto dto, BindingResult error) {
		if (error.hasErrors()) {
			return "admin/role/add";
		}
		if (roleService.addDto(dto))
			return "redirect:/admin/role";
		modelMap.addAttribute("message", "Can't insert ... !");
		return "admin/role/add";
	}

	@GetMapping("edit/{id}")
	public String edit(Model modelMap, @PathVariable("id") int id) {
		RoleDto dto = roleService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute("role", dto);
			return "admin/role/edit";
		}
		modelMap.addAttribute("message", "A role not exists !");
		return "redirect:/admin/role";

	}

	@PostMapping("edit")
	public String edit(Model modelMap, @Valid @ModelAttribute("role") RoleDto dto, BindingResult error) {
		if (error.hasErrors()) {
			return "admin/role/edit";
		}
		if (roleService.updateDto(dto)) {
			return "redirect:/admin/role";
		}
		modelMap.addAttribute("message", "Can't update ... !");
		return "admin/role/edit";

	}

	@GetMapping("delete/{id}")
	public String delete(Model modelMap, @PathVariable("id") int id) {
		if (!roleService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/admin/role";

	}
}
