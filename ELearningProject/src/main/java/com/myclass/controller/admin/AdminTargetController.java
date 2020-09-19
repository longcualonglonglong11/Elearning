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

import com.myclass.dto.TargetDto;
import com.myclass.entity.Target;
import com.myclass.service.CourseService;
import com.myclass.service.TargetService;
import com.myclass.service.TargetService;

@Controller
@RequestMapping("admin/target")
public class AdminTargetController {
	@Autowired
	private TargetService targetService;
	@Autowired
	private CourseService courseServive;

	@GetMapping("")
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("targets", targetService.findAllDto());
		return "admin/target/index";
	}

	@GetMapping("add")
	public String add(ModelMap modelMap) {
		modelMap.addAttribute("target", new TargetDto());
		modelMap.addAttribute("courses", courseServive.findAllDto());
		return "admin/target/add";
	}

	@PostMapping("add")
	public String add(ModelMap modelMap, @Valid @ModelAttribute("target") TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute("courses", courseServive.findAllDto());
			return "admin/target/add";
		}
		if (targetService.addDto(dto))
			return "redirect:/admin/target";
		modelMap.addAttribute("courses", courseServive.findAllDto());
		modelMap.addAttribute("message", "Can't insert ... !");
		return "admin/target/add";
	}

	@GetMapping("edit/{id}")
	public String edit(Model modelMap, @PathVariable("id") int id) {
		TargetDto dto = targetService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute("target", dto);
			modelMap.addAttribute("courses", courseServive.findAllDto());
			return "admin/target/edit";
		}
		modelMap.addAttribute("message", "A target not exists !");
		return "redirect:/admin/target";

	}

	@PostMapping("edit")
	public String edit(Model modelMap, @Valid @ModelAttribute("target") TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute("courses", courseServive.findAllDto());
			return "admin/target/edit";
		}
		if (targetService.updateDto(dto)) {
			return "redirect:/admin/target";
		}
		modelMap.addAttribute("courses", courseServive.findAllDto());
		modelMap.addAttribute("message", "Can't update ... !");
		return "admin/target/edit";

	}

	@GetMapping("delete/{id}")
	public String delete(Model modelMap, @PathVariable("id") int id) {
		if (!targetService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/admin/target";

	}
}
