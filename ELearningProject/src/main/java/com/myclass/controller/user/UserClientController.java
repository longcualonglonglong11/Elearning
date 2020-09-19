package com.myclass.controller.user;

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
import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.CategoryDto;
import com.myclass.dto.CourseDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.LoginDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.FilePath;
import com.myclass.service.CategoryService;
import com.myclass.service.CloudinaryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.TargetService;
import com.myclass.service.UserInformationService;
import com.myclass.service.UserService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping(value = { "client" })
public class UserClientController {
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
	@Autowired
	UserService userService;
	@Autowired
	CloudinaryService cloudinaryService;

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
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("videos", videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute("studentNumber", enrollmentService.countEnrollmentInCourse(id));
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));

		modelMap.addAttribute("course", courseDto);

		return "user/course/detail";
	}

	@RequestMapping(value = "/buy_course", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		int userId = userInformationService.getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		System.out.println("HAHAHAHAHAHHAH");
//		modelMap.addAttribute("courses", enrollmentService.findListCourseOfUserBuyed(userId));
		return "user/client/my_course";
	}

	@RequestMapping(value = "/mycourse", method = RequestMethod.GET)
	public String mycourse(ModelMap modelMap) {
		int userId = userInformationService.getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("courses", enrollmentService.findListCourseOfUserIsBuy(userId));
		return "user/client/my_course";
	}

	@RequestMapping(value = "/myprofile", method = RequestMethod.GET)
	public String myProfile(ModelMap modelMap) {
		int userId = userInformationService.getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("courses", enrollmentService.findListCourseOfUserIsBuy(userId));
		modelMap.addAttribute("user", userService.findDtoById(userId));
		return "user/client/my_profile";
	}

	@PostMapping(value = "/myprofile")
	public String saveProfile(ModelMap modelMap, @Valid @ModelAttribute("user") UserDto dto, BindingResult error) {
		int userId = userInformationService.getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("courses", enrollmentService.findListCourseOfUserIsBuy(userId));
		dto.setId(userId);
		if (error.hasErrors()) {

		}
		if (userService.updateProfile(dto)) {
			modelMap.addAttribute("messageContact", "Update Profile Success");
		}
		LoginDto loginDto = userInformationService.getUserInformation();
		loginDto.setFullname(dto.getFullname());
		UserDto newDto = userService.findDtoById(userId);
		modelMap.addAttribute("user", newDto);

		return "user/client/my_profile";
	}

	@RequestMapping(value = "/myprofile/picture")
	public String setPicture(ModelMap modelMap, @Valid @ModelAttribute("user") UserDto dto, BindingResult error) {
		int userId = userInformationService.getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("courses", enrollmentService.findListCourseOfUserIsBuy(userId));
		String cloudinaryImgURL;
//		UserDto dto = userService.findDtoById(userId);
		String oldImage = dto.getAvatar();

		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setId(userId);
		dto.setAvatar(cloudinaryImgURL);
		if (userService.updateProfilePicture(dto)) {
			modelMap.addAttribute("messagePicture", "Update Avatar Success");
		}
		LoginDto loginDto = userInformationService.getUserInformation();
		loginDto.setAvatar(cloudinaryImgURL);
		UserDto newDto = userService.findDtoById(userId);
		modelMap.addAttribute("user", newDto);
//		modelMap.addAttribute("user", dto);
		// modelMap.addAttribute("avatar", filePath);
		return "user/client/my_profile";
	}

	@RequestMapping(value = "/myprofile/security")
	public String setPassword(ModelMap modelMap, @Valid @ModelAttribute("user") UserDto dto,
			  BindingResult error) {
		int userId = userInformationService.getId();
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("cart", enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute("courses", enrollmentService.findListCourseOfUserIsBuy(userId));
		String confirmPass = dto.getConfirmPassword();
		if (confirmPass.equals(dto.getPassword())) {
			if (userService.updateSecurity(userId, dto.getPassword())) {
				modelMap.addAttribute("messageSecurity", "Change Password Success");
				System.out.println("kkk" + confirmPass);
			}
		}
		UserDto newDto = userService.findDtoById(userId);
		modelMap.addAttribute("user", newDto);

		return "user/client/my_profile";

	}
}
