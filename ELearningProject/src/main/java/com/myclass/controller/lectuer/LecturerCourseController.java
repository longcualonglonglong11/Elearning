package com.myclass.controller.lectuer;

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

import com.myclass.dto.CourseDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.LoginDto;
import com.myclass.dto.TargetDto;
import com.myclass.dto.UserDto;
import com.myclass.dto.VideoDto;
import com.myclass.entity.Category;
import com.myclass.entity.Course;
import com.myclass.entity.Target;
import com.myclass.entity.Video;
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
@RequestMapping(value = { "/lecturer/course" })
public class LecturerCourseController {
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

	@GetMapping("detail/{id}")
	public String index(Model modelMap, @PathVariable("id") int id) {
		modelMap.addAttribute("account", userInformationService.getUserInformation());

		int userId = userInformationService.getUserInformation().getId();
		EnrollmentDto dto = enrollmentService.findDto(userId, id);
		modelMap.addAttribute("categories", categoryService.findAllDto());

		modelMap.addAttribute("course", courseService.findDtoById(id));
		modelMap.addAttribute("targets", targetService.findAllDtoByCourseId(id));
		modelMap.addAttribute("videos", videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));
		modelMap.addAttribute("studentNumber", enrollmentService.countEnrollmentInCourse(id));
		return "lecturer/course/detail";
	}

	@GetMapping("add")
	public String add(Model modelMap) {
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("course", new CourseDto());
		return "lecturer/course/add";

	}

	@PostMapping("add")
	public String add(Model modelMap, @Valid @ModelAttribute("course") CourseDto dto, BindingResult error) {
		LoginDto account = userInformationService.getUserInformation();
		if (error.hasErrors()) {
			modelMap.addAttribute("account", account);
			modelMap.addAttribute("caterogies", categoryService.findAllDto());

			return "lecturer/course/add";
		}
		String oldImage = dto.getImage();
		String cloudinaryImgURL = "";
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setImage(cloudinaryImgURL);
		dto.setAuthor(account.getFullname());
		if (courseService.addDto(dto)) {
			return "redirect:/lecturer";
		}
		modelMap.addAttribute("account", account);
		modelMap.addAttribute("caterogies", categoryService.findAllDto());
		modelMap.addAttribute("message", "Can't insert ... !");
		return "lecturer/course/add";

	}

	@GetMapping("edit/{id}")
	public String edit(Model modelMap, @PathVariable("id") int id) {
		modelMap.addAttribute("account", userInformationService.getUserInformation());

		CourseDto dto = courseService.findDtoById(id);

		if (dto != null) {
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));
			modelMap.addAttribute("course", dto);
			modelMap.addAttribute("caterogies", categoryService.findAll());
			return "lecturer/course/edit";
		}
		modelMap.addAttribute("message", "A course not exists !");
		return "redirect:/lecturer/course/edit/" + id;

	}

	@PostMapping("edit")
	public String edit(Model modelMap, @Valid @ModelAttribute("course") CourseDto dto, BindingResult error) {
		LoginDto account = userInformationService.getUserInformation();

		if (error.hasErrors()) {
			modelMap.addAttribute("account", account);
			modelMap.addAttribute("caterogies", categoryService.findAllDto());
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getId()));

			return "lecturer/course/edit";
		}
		String oldImage = dto.getImage();
		String cloudinaryImgURL = "";
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setImage(cloudinaryImgURL);
		dto.setAuthor(account.getFullname());
		if (courseService.updateDto(dto)) {
			return "redirect:/lecturer/course/detail/" + dto.getId();
		}
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getId()));
		modelMap.addAttribute("caterogies", categoryService.findAllDto());
		modelMap.addAttribute("message", "Can't update ... !");
		return "redirect:/lecturer/course/detail/" + dto.getId();

	}

	@GetMapping("video/add/{id}")
	public String addVideo(Model modelMap, @PathVariable("id") int id) {
		VideoDto dto = new VideoDto();
		CourseDto tmpCourse = courseService.findDtoById(id);

		if (tmpCourse != null) {

			dto.setCourseId(tmpCourse.getId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("video", dto);
			modelMap.addAttribute("account", userInformationService.getUserInformation());

			modelMap.addAttribute("course", courseService.findDtoById(id));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));
			modelMap.addAttribute("categories", categoryService.findAllDto());

			return "lecturer/course/video_in_course/add";
		}
		return "redirect:/lecturer/course/detail/" + id;
	}

	@PostMapping("video/add")
	public String addVideo(Model modelMap, @Valid @ModelAttribute("video") VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("caterogies", categoryService.findAll());

			modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));
			modelMap.addAttribute("video", dto);
			return "lecturer/course/video_in_course/add";
		}
		if (videoService.addDto(dto)) {
			return "redirect:/lecturer/course/detail/" + dto.getCourseId();

		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute("video", dto);
		modelMap.addAttribute("message", "Can't insert ... !");
		return "lecturer/course/video_in_course/add";

	}

	@GetMapping("video/edit/{id}")
	public String editVideo(Model modelMap, @PathVariable("id") int id) {
		VideoDto dto = videoService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute("categories", categoryService.findAllDto());

			modelMap.addAttribute("video", dto);
			modelMap.addAttribute("account", userInformationService.getUserInformation());

			modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));

			return "lecturer/course/video_in_course/edit";
		}
		return "redirect:/lecturer";
	}

	@PostMapping("video/edit")
	public String editVideo(Model modelMap, @Valid @ModelAttribute("video") VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("account", userInformationService.getUserInformation());

			modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));
			modelMap.addAttribute("video", dto);
			return "lecturer/course/video_in_course/edit";
		}
		if (videoService.updateDto(dto)) {
			return "redirect:/lecturer/course/detail/" + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));
		modelMap.addAttribute("video", dto);
		modelMap.addAttribute("message", "Can't update ... !");
		return "lecturer/course/video_in_course/edit";

	}

	@GetMapping("video/delete/{id}")
	public String deleteVideo(Model modelMap, @PathVariable("id") int id) {
		VideoDto dto = videoService.findDtoById(id);
		if (!videoService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/lecturer/course/detail/" + dto.getCourseId();

	}

	@GetMapping("target/add/{id}")
	public String addTarget(Model modelMap, @PathVariable("id") int id) {
		TargetDto dto = new TargetDto();
		CourseDto tmpCourse = courseService.findDtoById(id);
		if (tmpCourse != null) {
			dto.setCourseId(tmpCourse.getId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("course", courseService.findDtoById(id));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));
			modelMap.addAttribute("target", dto);
			return "lecturer/course/target_in_course/add";
		}
		return "redirect:/lecturer/course/detail/" + id;
	}

	@PostMapping("target/add")
	public String addTarget(Model modelMap, @Valid @ModelAttribute("target") TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("target", dto);
			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));
			return "lecturer/course/target_in_course/add";
		}
		if (targetService.addDto(dto)) {

			return "redirect:/lecturer/course/detail/" + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute("target", dto);
		modelMap.addAttribute("message", "Can't insert ... !");
		return "lecturer/course/target_in_course/add";

	}

	@GetMapping("target/edit/{id}")
	public String editTarget(Model modelMap, @PathVariable("id") int id) {
		TargetDto dto = targetService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute("target", dto);
			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));
			return "lecturer/course/target_in_course/edit";
		}
		return "redirect:/lecturer";
	}

	@PostMapping("target/edit")
	public String editTarget(Model modelMap, @Valid @ModelAttribute("target") TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("target", dto);
			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));
			return "lecturer/course/target_in_course/edit";
		}
		if (targetService.updateDto(dto)) {
			return "redirect:/lecturer/course/detail/" + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute("target", dto);
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));
		modelMap.addAttribute("message", "Can't update ... !");
		return "lecturer/course/target_in_course/edit";

	}

	@GetMapping("target/delete/{id}")
	public String deleteTarget(Model modelMap, @PathVariable("id") int id) {
		TargetDto dto = targetService.findDtoById(id);
		if (!targetService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/lecturer/course/detail/" + dto.getCourseId();

	}

	@GetMapping("/member/{id}")
	public String member(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.addAttribute("coHostId", roleService.findDtoByName("ROLE_SUB_LECTURER").getId());
		modelMap.addAttribute("users", enrollmentService.findAllMember(id));
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("course", courseService.findDtoById(id));
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));
		return "lecturer/course/member_in_course/index";
	}

	@GetMapping("member/delete/{id}/{courseId}")

	public String kichOut(Model modelMap, @PathVariable("id") int id, @PathVariable("courseId") int courseId) {
		UserDto dto = userService.findDtoById(id);
		if (dto.getRoleName().equals("ROLE_LECTURER") || dto.getRoleName().equals("ROLE_ADMIN")
				|| !enrollmentService.kikAMember(courseId, id) || !userService.cancleToMember(id, courseId)) {
			modelMap.addAttribute("message", "Can't delete ... !");
			modelMap.addAttribute("coHostId", roleService.findDtoByName("ROLE_SUB_LECTURER").getId());
			modelMap.addAttribute("users", enrollmentService.findAllMember(courseId));
			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("course", courseService.findDtoById(courseId));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(courseId));
			modelMap.addAttribute("message","Can't kik because this user has higher right than you...!");
			return "lecturer/course/member_in_course/index";

		}
		return "redirect:/lecturer/course/member/" + courseId;

	}

	@GetMapping("member/add/{id}")
	public String addMember(Model modelMap, @PathVariable("id") int id) {
		// TargetDto dto = new TargetDto();
		CourseDto tmpCourse = courseService.findDtoById(id);
		EnrollmentDto dto = new EnrollmentDto();
		dto.setCourseName(tmpCourse.getTitle());
		dto.setCourseId(id);
		if (tmpCourse != null) {
//			dto.setCourseId(tmpCourse.getId());
//			dto.setCourseName(tmpCourse.getTitle());

			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("course", tmpCourse);
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(id));
			modelMap.addAttribute("users", enrollmentService.findAllWhoNotMember(id));
			modelMap.addAttribute("enrollment", dto);
			return "lecturer/course/member_in_course/add";
		}
		return "redirect:/lecturer/course/member/" + id;
	}

	@PostMapping("member/add")
	public String addMember(Model modelMap, @Valid @ModelAttribute("enrollment") EnrollmentDto dto,
			BindingResult error) {

		if (error.hasErrors()) {
			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));
			return "lecturer/course/member_in_course/add";
		}
		if (enrollmentService.addMember(dto)) {

			return "redirect:/lecturer/course/member/" + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute("account", userInformationService.getUserInformation());
		modelMap.addAttribute("categories", categoryService.findAllDto());
		modelMap.addAttribute("course", courseService.findDtoById(dto.getCourseId()));
		modelMap.addAttribute("category", courseService.findCategoryByCourseId(dto.getCourseId()));
		modelMap.addAttribute("message", "Can't add member ... !");
		return "lecturer/course/member_in_course/add";

	}

	@GetMapping("member/authorize/{id}/{courseId}")

	public String authorize(Model modelMap, @PathVariable("id") int id, @PathVariable("courseId") int courseId) {
		UserDto dto = userService.findDtoById(id);

		if (dto.getRoleName().equals("ROLE_ADMIN") || dto.getRoleName().equals("ROLE_LECTURER")
				|| !userService.authorizeToCoHost(id, courseId)) {
			modelMap.addAttribute("coHostId", roleService.findDtoByName("ROLE_SUB_LECTURER").getId());
			modelMap.addAttribute("users", enrollmentService.findAllMember(courseId));
			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("course", courseService.findDtoById(courseId));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(courseId));
			modelMap.addAttribute("message",
					"Can't authorize because this user has higher right or equal right than you... !");
			return "lecturer/course/member_in_course/index";

		}
		return "redirect:/lecturer/course/member/" + courseId;

	}

	@GetMapping("member/cancel/{id}/{courseId}")

	public String cancleRight(Model modelMap, @PathVariable("id") int id, @PathVariable("courseId") int courseId) {
		UserDto dto = userService.findDtoById(id);
		if (!dto.getRoleName().equals("ROLE_SUB_LECTURER") || !userService.cancleToMember(id, courseId)) {
			modelMap.addAttribute("coHostId", roleService.findDtoByName("ROLE_SUB_LECTURER").getId());
			modelMap.addAttribute("users", enrollmentService.findAllMember(courseId));
			modelMap.addAttribute("account", userInformationService.getUserInformation());
			modelMap.addAttribute("categories", categoryService.findAllDto());
			modelMap.addAttribute("course", courseService.findDtoById(courseId));
			modelMap.addAttribute("category", courseService.findCategoryByCourseId(courseId));
			modelMap.addAttribute("message", "Can't cancel ... !");
			return "lecturer/course/member_in_course/index";

		}
		return "redirect:/lecturer/course/member/" + courseId;

	}
}
