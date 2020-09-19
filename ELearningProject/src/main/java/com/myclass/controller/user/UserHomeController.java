package com.myclass.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myclass.dto.CategoryDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.UserInformationService;

@Controller
@RequestMapping(value = { "/", "", "/home" })
public class UserHomeController {
	@Autowired
	CourseService courseService;
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	CategoryService categoryService;
	@Autowired 
	UserInformationService userInformationService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		int userId = userInformationService.getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("coursesOnSale", courseService.findAllHomeCouseDtoOnSale(8));
		modelMap.addAttribute("coursesPopular", courseService.findAllHomeCouseDtoTopSeller(8));
		modelMap.addAttribute("coursesPopularCharged", courseService.findAllChargedHomeCouseDtoTopSeller(8));
		modelMap.addAttribute("coursesPopularFree", courseService.findAllFreeHomeCouseDtoTopSeller(8));

		modelMap.addAttribute("categories", categoryService.findAllDto());
		return "user/home/index";
	}
	@PostMapping("/search")
	public String index(Model modelMap, @RequestParam(name = "querry") String querry) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());

		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("querry", querry);
		modelMap.addAttribute("courses", courseService.searchCourseByTitle(querry));
		return "user/home/search";
	}
}
