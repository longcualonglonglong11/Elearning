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
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.myclass.dto.CourseDto;
import com.myclass.dto.TargetDto;
import com.myclass.dto.VideoDto;
import com.myclass.entity.Category;
import com.myclass.entity.Course;
import com.myclass.entity.Target;
import com.myclass.entity.Video;
import com.myclass.service.CategoryService;
import com.myclass.service.CloudinaryService;
import com.myclass.service.CourseService;
import com.myclass.service.TargetService;
import com.myclass.service.UserService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping("admin/course")
public class AdminCourseController {
	@Autowired
	CourseService courseService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	VideoService videoService;
	@Autowired
	TargetService targetService;
	@Autowired
	UserService userService;
	@Autowired
	CloudinaryService cloudinaryService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("courses", courseService.findAllDto());
		return "admin/course/index";
	}

	@GetMapping("add")
	public String add(ModelMap modelMap) {
		modelMap.addAttribute("lecturers", userService.findDtoByAllLecturer());

		modelMap.addAttribute("caterogies", categoryService.findAllDto());
		modelMap.addAttribute("course", new CourseDto());
		return "admin/course/add";
	}

	@PostMapping("add")
	public String add(ModelMap modelMap, @Valid @ModelAttribute("course") CourseDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute("caterogies", categoryService.findAllDto());
			return "admin/course/add";
		}
		String oldImage = dto.getImage();
		String cloudinaryImgURL = "";
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setImage(cloudinaryImgURL);
		if (courseService.addDto(dto))
			return "redirect:/admin/course";

		modelMap.addAttribute("caterogies", categoryService.findAllDto());
		modelMap.addAttribute("message", "Can't insert ... !");
		return "admin/course/add";
	}

	@GetMapping("edit/{id}")
	public String edit(Model modelMap, @PathVariable("id") int id) {
		CourseDto dto = courseService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute("lecturers", userService.findDtoByAllLecturer());
			modelMap.addAttribute("course", dto);
			modelMap.addAttribute("caterogies", categoryService.findAll());
			return "admin/course/edit";
		}
		modelMap.addAttribute("message", "A course not exists !");
		return "redirect:/admin/course";

	}

	@PostMapping("edit")
	public String edit(Model modelMap, @Valid @ModelAttribute("course") CourseDto dto, BindingResult error) {
		System.out.println(dto.toString());
		if (error.hasErrors()) {
			modelMap.addAttribute("caterogies", categoryService.findAllDto());
			return "admin/course/edit";
		}
		String oldImage = dto.getImage();
		String cloudinaryImgURL = "";
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setImage(cloudinaryImgURL);
		if (courseService.updateDto(dto)) {
			return "redirect:/admin/course";
		}
		modelMap.addAttribute("caterogies", categoryService.findAllDto());
		modelMap.addAttribute("message", "Can't update ... !");
		return "admin/course/edit";

	}

	@GetMapping("delete/{id}")
	public String delete(Model modelMap, @PathVariable("id") int id) {
		if (!courseService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/admin/course";

	}

	@GetMapping("video/{id}")
	public String search(Model modelMap, @PathVariable("id") int id) {

		modelMap.addAttribute("course", courseService.findDtoById(id));
		modelMap.addAttribute("videos", videoService.findAllDtoByCourseId(id));
		return "admin/course/video_in_course/index";

	}

	@GetMapping("video/add/{id}")
	public String addVideo(Model modelMap, @PathVariable("id") int id) {
		VideoDto dto = new VideoDto();
		CourseDto tmpCourse = courseService.findDtoById(id);
		if (tmpCourse != null) {
			dto.setCourseId(tmpCourse.getId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("video", dto);
			return "admin/course/video_in_course/add";
		}
		return "redirect:/admin/course";
	}

	@PostMapping("video/add")
	public String addVideo(Model modelMap, @Valid @ModelAttribute("video") VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("video", dto);
			return "admin/course/video_in_course/add";
		}
		if (videoService.addDto(dto)) {
			return "redirect:/admin/course/video/" + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute("video", dto);
		modelMap.addAttribute("message", "Can't insert ... !");
		return "admin/course/video_in_course/add";

	}

	@GetMapping("video/edit/{id}")
	public String editVideo(Model modelMap, @PathVariable("id") int id) {
		VideoDto dto = videoService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute("video", dto);
			return "admin/course/video_in_course/edit";
		}
		return "redirect:/admin/course";
	}

	@PostMapping("video/edit")
	public String editVideo(Model modelMap, @Valid @ModelAttribute("video") VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("video", dto);
			return "admin/course/video_in_course/edit";
		}
		if (videoService.updateDto(dto)) {
			return "redirect:/admin/course/video/" + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute("video", dto);
		modelMap.addAttribute("message", "Can't update ... !");
		return "admin/course/video_in_course/add";

	}

	@GetMapping("video/delete/{id}")
	public String deleteVideo(Model modelMap, @PathVariable("id") int id) {
		Optional<Video> video = videoService.findById(id);
		if (!videoService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/admin/course/video/" + video.get().getCourseId();

	}

	@GetMapping("target/{id}")
	public String indexTarget(Model modelMap, @PathVariable("id") int id) {
		modelMap.addAttribute("target", targetService.findDtoById(id));

		modelMap.addAttribute("targets", targetService.findAllDtoByCourseId(id));
		return "admin/course/target_in_course/index";

	}

	@GetMapping("target/add/{id}")
	public String addTarget(Model modelMap, @PathVariable("id") int id) {
		TargetDto dto = new TargetDto();
		CourseDto tmpCourse = courseService.findDtoById(id);
		if (tmpCourse != null) {
			dto.setCourseId(tmpCourse.getId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("target", dto);
			return "admin/course/target_in_course/add";
		}
		return "redirect:/admin/course";
	}

	@PostMapping("target/add")
	public String addTarget(Model modelMap, @Valid @ModelAttribute("target") TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("target", dto);

			return "admin/course/target_in_course/add";
		}
		if (targetService.addDto(dto)) {
			return "redirect:/admin/course/target/" + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute("target", dto);
		modelMap.addAttribute("message", "Can't insert ... !");
		return "admin/course/target_in_course/add";

	}

	@GetMapping("target/edit/{id}")
	public String editTarget(Model modelMap, @PathVariable("id") int id) {
		TargetDto dto = targetService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute("target", dto);
			return "admin/course/target_in_course/edit";
		}
		return "redirect:/admin/course";
	}

	@PostMapping("target/edit")
	public String editTarget(Model modelMap, @Valid @ModelAttribute("target") TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute("target", dto);
			return "admin/course/target_in_course/edit";
		}
		if (targetService.updateDto(dto)) {
			return "redirect:/admin/course/target/" + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute("target", dto);
		modelMap.addAttribute("message", "Can't update ... !");
		return "admin/course/target_in_course/edit";

	}

	@GetMapping("target/delete/{id}")
	public String deleteTarget(Model modelMap, @PathVariable("id") int id) {
		Optional<Target> target = targetService.findById(id);
		if (!targetService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/admin/course/target/" + target.get().getCourseId();

	}
}
