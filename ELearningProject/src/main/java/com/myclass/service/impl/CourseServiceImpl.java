package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myclass.dto.CategoryDto;
import com.myclass.dto.CourseDto;
import com.myclass.dto.HomeCourseDto;
import com.myclass.dto.RoleDto;
import com.myclass.entity.Category;
import com.myclass.entity.Course;
import com.myclass.entity.Enrollment;
import com.myclass.entity.Role;
import com.myclass.entity.Course;
import com.myclass.repository.CourseRepository;
import com.myclass.repository.EnrollmentRepository;
import com.myclass.repository.RoleRepository;
import com.myclass.repository.TargetRepository;
import com.myclass.repository.VideoRepository;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.VideoService;

@Service
public class CourseServiceImpl extends GenericServiceImpl<Course, Integer> implements CourseService {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	TargetRepository targetRepository;
	@Autowired
	CategoryService categoryService;
	@Autowired
	VideoService videoService;

	public List<CourseDto> findAllDto() {
		List<Course> courses = courseRepository.findAll();
		List<CourseDto> dtos = new ArrayList<CourseDto>();
		for (Course course : courses) {
			int couseId = course.getId();
			CourseDto dto = new CourseDto();
			dto.setId(course.getId());
			dto.setTitle(course.getTitle());
			dto.setImage(course.getImage());
			dto.setDiscount(course.getDiscount());
			dto.setPrice(course.getPrice());
			int lectureCount = videoRepository.countByCourseId(couseId);
			dto.setLectureCount(lectureCount);
			int targetCount = targetRepository.countByCourseId(couseId);
			dto.setTargetCount(targetCount);
			int sumLengthOfAllVideos = 0;
			try {
				sumLengthOfAllVideos = videoRepository.sumLenghtByCourseId(couseId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setLengthVideos(sumLengthOfAllVideos);
			dto.setCategoryName(course.getCategory().getTitle());
			dto.setAuthor(course.getAuthor());
			dtos.add(dto);
		}
		return dtos;

	}

	@Override
	public boolean addDto(CourseDto dto) {
		try {
			Course course = new Course();
			course.setTitle(dto.getTitle());
			course.setImage(dto.getImage());
			course.setPrice(dto.getPrice());
			course.setDiscount(dto.getDiscount());
			course.setDescription(dto.getDescription());
			course.setContent(dto.getContent());
			course.setCategoryId(dto.getCategoryId());
			course.setLastUpdate(new Date());
			float promotionPrice = course.getPrice() - course.getPrice() * course.getDiscount() / 100;
			course.setPromotionPrice(promotionPrice);
			course.setAuthor(dto.getAuthor());
			courseRepository.save(course);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDto(CourseDto dto) {
		try {
			Course course = new Course();
			course.setId(dto.getId());
			course.setTitle(dto.getTitle());
			course.setImage(dto.getImage());
			course.setPrice(dto.getPrice());
			course.setDiscount(dto.getDiscount());
			course.setDescription(dto.getDescription());
			course.setContent(dto.getContent());
			course.setCategoryId(dto.getCategoryId());
			course.setLastUpdate(new Date());
			float promotionPrice = course.getPrice() - course.getPrice() * course.getDiscount() / 100;
			course.setPromotionPrice(promotionPrice);
			course.setAuthor(dto.getAuthor());
			courseRepository.saveAndFlush(course);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CourseDto findDtoById(int id) {
		Optional<Course> course = courseRepository.findById(id);
		CourseDto dto = new CourseDto();

		dto.setId(id);
		dto.setTitle(course.get().getTitle());
		dto.setImage(course.get().getImage());
		dto.setDiscount(course.get().getDiscount());
		dto.setPrice(course.get().getPrice());
		dto.setCategoryName(course.get().getCategory().getTitle());
		dto.setCategoryId(course.get().getCategoryId());
		dto.setAuthor(course.get().getAuthor());
		dto.setDescription(course.get().getDescription());
		dto.setLastUpdate(course.get().getLastUpdate());
		dto.setContent(course.get().getContent());
		int lectureCount = videoService.coutVideoInCourse(id);
		dto.setLectureCount(lectureCount);
		int sumLengthOfAllVideos = 0;
		try {
			sumLengthOfAllVideos = videoService.sumLengthAllVideoInCourse(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dto.setLectureCount(lectureCount);
		dto.setLengthVideos(sumLengthOfAllVideos);
		return dto;
	}

	@Override
	public List<HomeCourseDto> findAllHomeCouseDtoOnSale(int top) {
		List<Course> courses = courseRepository.findTopCourseOnSale(PageRequest.of(0, top));
		List<HomeCourseDto> dtos = new ArrayList<HomeCourseDto>();
		for (Course course : courses) {
			if (course.getDiscount() > 0) {
				HomeCourseDto dto = new HomeCourseDto();
				dto.setId(course.getId());
				dto.setTitle(course.getTitle());
				dto.setImage(course.getImage());
				dto.setDiscount((int) course.getDiscount());
				dto.setPrice(course.getPrice());
				dto.setAuthor(course.getAuthor());
				dto.setPromotionPrice(course.getPromotionPrice());
				dto.setDescription(course.getDescription());
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Override
	public List<HomeCourseDto> findAllHomeCouseDtoTopSeller(int top) {
		List<Enrollment> enrollments = enrollmentRepository.findTopSellerCourseId(PageRequest.of(0, top));
		List<HomeCourseDto> dtos = new ArrayList<HomeCourseDto>();
		for (Enrollment enrollment : enrollments) {
			int id = enrollment.getCourseId();
			Course course = findById(id).get();
			HomeCourseDto dto = new HomeCourseDto();
			dto.setId(course.getId());
			dto.setTitle(course.getTitle());
			dto.setImage(course.getImage());
			dto.setPrice(course.getPrice());
			dto.setAuthor(course.getAuthor());
			dto.setDescription(course.getDescription());
			int totalMember = enrollmentRepository.countByCourseId(course.getId());
			dto.setTotalMembers(totalMember);
			dtos.add(dto);

		}
		return dtos;

	}

	public List<HomeCourseDto> findAllChargedHomeCouseDtoTopSeller(int top) {
		List<Enrollment> enrollments = enrollmentRepository.findTopSellerCourseId(PageRequest.of(0, top));
		List<HomeCourseDto> dtos = new ArrayList<HomeCourseDto>();
		for (Enrollment enrollment : enrollments) {
			int id = enrollment.getCourseId();
			Course course = findById(id).get();
			if (course.getPromotionPrice() > 0) {
				
				HomeCourseDto dto = new HomeCourseDto();
				dto.setId(course.getId());
				dto.setTitle(course.getTitle());
				dto.setImage(course.getImage());
				dto.setPrice(course.getPrice());
				dto.setAuthor(course.getAuthor());
				dto.setDescription(course.getDescription());
				int totalMember = enrollmentRepository.countByCourseId(course.getId());
				dto.setTotalMembers(totalMember);
				dtos.add(dto);
			}
		}
		return dtos;

	}
	public List<HomeCourseDto> findAllFreeHomeCouseDtoTopSeller(int top) {
		List<Enrollment> enrollments = enrollmentRepository.findTopSellerCourseId(PageRequest.of(0, top));
		List<HomeCourseDto> dtos = new ArrayList<HomeCourseDto>();
		for (Enrollment enrollment : enrollments) {
			int id = enrollment.getCourseId();
			Course course = findById(id).get();
			if (course.getPromotionPrice() == 0) {

				HomeCourseDto dto = new HomeCourseDto();
				dto.setId(course.getId());
				dto.setTitle(course.getTitle());
				dto.setImage(course.getImage());
				dto.setPrice(course.getPrice());
				dto.setAuthor(course.getAuthor());
				dto.setDescription(course.getDescription());
				int totalMember = enrollmentRepository.countByCourseId(course.getId());
				dto.setTotalMembers(totalMember);
				dtos.add(dto);
			}
		}
		return dtos;

	}
	@Override
	public CategoryDto findCategoryByCourseId(int courseId) {
		Optional<Course> optionCourse = courseRepository.findById(courseId);
		Course course = optionCourse.get();
//		Category category = dto.getCategory();
		CategoryDto dto = categoryService.findDtoById(course.getCategoryId());
		return dto;
	}

	@Override
	public List<CourseDto> findAllDtoByCategoryId(int categoryId) {
		List<Course> courses = courseRepository.findByCategoryId(categoryId);
		List<CourseDto> dtos = new ArrayList<CourseDto>();
		for (Course course : courses) {
			int couseId = course.getId();
			CourseDto dto = new CourseDto();
			dto.setId(course.getId());
			dto.setTitle(course.getTitle());
			dto.setImage(course.getImage());
			dto.setDiscount(course.getDiscount());
			dto.setPrice(course.getPrice());
			int lectureCount = videoRepository.countByCourseId(couseId);
			dto.setLectureCount(lectureCount);
			int targetCount = targetRepository.countByCourseId(couseId);
			dto.setTargetCount(targetCount);
			int sumLengthOfAllVideos = 0;
			try {
				sumLengthOfAllVideos = videoRepository.sumLenghtByCourseId(couseId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setLengthVideos(sumLengthOfAllVideos);
			dto.setCategoryName(course.getCategory().getTitle());
			dto.setAuthor(course.getAuthor());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<CourseDto> searchCourseByTitle(String title) {
		List<Course> courses = courseRepository.findByTitleContaining(title);
		List<CourseDto> dtos = new ArrayList<CourseDto>();
		for (Course course : courses) {
			int couseId = course.getId();
			CourseDto dto = new CourseDto();
			dto.setId(course.getId());
			dto.setTitle(course.getTitle());
			dto.setImage(course.getImage());
			dto.setDiscount(course.getDiscount());
			dto.setPrice(course.getPrice());
			int lectureCount = videoRepository.countByCourseId(couseId);
			dto.setLectureCount(lectureCount);
			int targetCount = targetRepository.countByCourseId(couseId);
			dto.setTargetCount(targetCount);
			int sumLengthOfAllVideos = 0;
			try {
				sumLengthOfAllVideos = videoRepository.sumLenghtByCourseId(couseId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setLengthVideos(sumLengthOfAllVideos);
			dto.setCategoryName(course.getCategory().getTitle());
			dto.setAuthor(course.getAuthor());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<CourseDto> findAllCourseOfLecturer(String author) {
		return courseRepository.findAllCourseDtoByAuthorName(author);
	}

	@Autowired
	RoleRepository roleRepository;

	@Override
	public CourseDto findAllCourseOfSubLecturer(int id) {
		// TODO Auto-generated method stub
		Role role = roleRepository.findFirstByName("ROLE_SUB_LECTURER");
		return courseRepository.findACourseDtoByUserIdAndRoleId(id, role.getId());
	}
}
