package com.myclass.controller.admin;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.dto.VideoDto;
import com.myclass.entity.Video;
import com.myclass.repository.VideoRepository;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping("admin/video")
public class AdminVideoController {
	@Autowired
	VideoService videoService;
	@Autowired
	CourseService courseService;
	@Autowired
	CategoryService categoryService;
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		
		modelMap.addAttribute("videos", videoService.findAllDto());
		return "admin/video/index";
	}
	@GetMapping("add")
	public String add(ModelMap modelMap) {
		modelMap.addAttribute("courses", courseService.findAllDto());
		modelMap.addAttribute("video", new VideoDto());
		return "admin/video/add";
	}
	@PostMapping("add")
	public String add(ModelMap modelMap, @Valid @ModelAttribute("video") VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute("courses", courseService.findAllDto());
			return "admin/video/add";
		}
	
		if (videoService.addDto(dto))
			return "redirect:/admin/video";
		modelMap.addAttribute("courses", courseService.findAllDto());
		modelMap.addAttribute("message", "Can't insert ... !");
		return "admin/video/add";
	}
	@GetMapping("edit/{id}")
	public String edit(Model modelMap, @PathVariable("id") int id) {
		VideoDto video = videoService.findDtoById(id);
		if (video != null) {
			modelMap.addAttribute("video", video);
			modelMap.addAttribute("courses", courseService.findAllDto());
			return "admin/video/edit";
		}
		modelMap.addAttribute("message", "A video not exists !");
		return "redirect:/admin/video";

	}
	@PostMapping("edit")
	public String edit(Model modelMap, @Valid @ModelAttribute("video") VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute("courses", courseService.findAllDto());
			return "admin/video/edit";
		}
		if (videoService.updateDto(dto)) {
			return "redirect:/admin/video";
		}
		modelMap.addAttribute("courses", courseService.findAllDto());
		modelMap.addAttribute("message", "Can't update ... !");
		return "admin/video/edit";

	}

	@GetMapping("delete/{id}")
	public String delete(Model modelMap, @PathVariable("id") int id) {
		if (!videoService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/admin/video";

	}
	
}

