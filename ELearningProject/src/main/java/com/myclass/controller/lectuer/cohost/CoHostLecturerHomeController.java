package com.myclass.controller.lectuer.cohost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.dto.CourseDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.LoginDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CloudinaryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.RoleService;
import com.myclass.service.TargetService;
import com.myclass.service.UserInformationService;
import com.myclass.service.UserService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping(value = { "/sublecturer", "/sublecturer/home" })
public class CoHostLecturerHomeController {
	@Autowired
	CourseService courseService;
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	TargetService targetService;
	@Autowired
	VideoService videoService;
	@Autowired
	UserInformationService userInformationService;
	@Autowired
	CloudinaryService cloudinaryService;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		CourseDto dto = courseService.findAllCourseOfSubLecturer(userId);
		int id = dto.getId();
		modelMap.addAttribute("categories", categoryService.findAllDto());

		modelMap.addAttribute("course", courseService.findDtoById(id));
		modelMap.addAttribute("targets", targetService.findAllDtoByCourseId(id));
		modelMap.addAttribute("videos", videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));
		modelMap.addAttribute("studentNumber", enrollmentService.countEnrollmentInCourse(id));
		return "lecturer/course/detail";
	}
}
