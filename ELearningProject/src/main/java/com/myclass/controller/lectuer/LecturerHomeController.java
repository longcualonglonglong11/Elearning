package com.myclass.controller.lectuer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.dto.LoginDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.UserInformationService;
@Controller
@RequestMapping(value = { "/lecturer", "/lecturer/home" })
public class LecturerHomeController {
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
		LoginDto account = userInformationService.getUserInformation();
		modelMap.addAttribute("account", account);
		//next
		modelMap.addAttribute("courses", courseService.findAllCourseOfLecturer(account.getFullname()));
		modelMap.addAttribute("categories", categoryService.findAllDto());
		return "lecturer/home/index";
	}
}
