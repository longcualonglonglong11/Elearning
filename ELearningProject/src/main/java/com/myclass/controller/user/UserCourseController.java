package com.myclass.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.dto.CourseDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.LoginDto;
import com.myclass.dto.TargetDto;
import com.myclass.repository.TargetRepository;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.TargetService;
import com.myclass.service.UserInformationService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping(value = { "course" })
public class UserCourseController {
	@Autowired
	CourseService courseService;
	@Autowired
	TargetService targetService;
	@Autowired
	VideoService videoService;
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserInformationService userInformationService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		return "user/course/index";
	}

	@GetMapping("detail/{id}")
	public String index(Model modelMap, @PathVariable("id") int id) {

		int userId = userInformationService.getUserInformation().getId();
//		boolean isEnroll = enrollmentService.enroll(userId, id);
		EnrollmentDto dto = enrollmentService.findDto(userId, id);
		if (dto == null) {
			dto = new EnrollmentDto();
			dto.setInCart(false);
			dto.setBuy(false);
		}
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("course", courseService.findDtoById(id));
		modelMap.addAttribute("targets", targetService.findAllDtoByCourseId(id));
		modelMap.addAttribute("videos", videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute("isInCart", dto.isInCart());
		modelMap.addAttribute("isBuy", dto.isBuy());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));
		modelMap.addAttribute("studentNumber", enrollmentService.countEnrollmentInCourse(id));
		return "user/course/detail";
	}

	@GetMapping("cart/{id}")
	public String indexCart(Model modelMap, @PathVariable("id") int id) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());

		EnrollmentDto dto = enrollmentService.findDto(userId, id);
		if (dto != null && dto.isInCart()) {
			enrollmentService.removeCart(userId, id);
		} else {
			enrollmentService.enroll(userId, id);

		}

		return "redirect:/course/detail/" + id;
	}

	@GetMapping("buy_course/{id}")
	public String buy(Model modelMap, @PathVariable("id") int id) {
		int userId = userInformationService.getUserInformation().getId();
		LoginDto account = userInformationService.getUserInformation();
		modelMap.addAttribute("account", account);
		CourseDto courseDto = courseService.findDtoById(id);
		double priceOfCourse = (double) (courseDto.getPrice() - (courseDto.getPrice() * courseDto.getDiscount() / 100));
		double restBalance = account.getBalance() - priceOfCourse;
		System.out.println("BAAAAAAAAAAAAAAAA: " + restBalance);
		if (restBalance < 0) {
			modelMap.addAttribute("message", "Not enough money to buy this course ...!");
			modelMap.addAttribute("isBuy", false);

		} else {
			enrollmentService.buyCourse(userId, id, restBalance);
			account.setBalance(restBalance);

			modelMap.addAttribute("isBuy", true);
		}
		EnrollmentDto dto = enrollmentService.findDto(userId, id);
		modelMap.addAttribute("isInCart", dto.isInCart());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("videos", videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute("studentNumber", enrollmentService.countEnrollmentInCourse(id));
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));

		modelMap.addAttribute("course", courseDto);

		return "user/course/detail";
	}

	@GetMapping("cart")
	public String cart(ModelMap modelMap) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());

		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("courses", enrollmentService.findListCourseOfUserInCart(userId));
		return "user/client/cart";
	}

	@GetMapping("category/{id}")
	public String indexCategory(Model modelMap, @PathVariable("id") int id) {
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("category", categoryService.findDtoById(id));

		modelMap.addAttribute("courses", courseService.findAllDtoByCategoryId(id));
		return "user/category/index";
	}

}
