package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.CourseDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.RoleDto;
import com.myclass.dto.UserDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.entity.Course;
import com.myclass.entity.Enrollment;
import com.myclass.entity.Role;
import com.myclass.entity.Enrollment;
import com.myclass.repository.CourseRepository;
import com.myclass.repository.EnrollmentRepository;
import com.myclass.repository.RoleRepository;
import com.myclass.repository.UserRepository;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.UserService;

@Service
public class EnrollmentServiceImpl extends GenericServiceImpl<Enrollment, Integer> implements EnrollmentService {
	@Autowired
	EnrollmentRepository enrollmentRepository;
	@Autowired
	CourseService courseService;
	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Override
	public List<EnrollmentDto> findAllDto() {
		List<Enrollment> enrollments = enrollmentRepository.findAll();
		List<EnrollmentDto> dtos = new ArrayList<EnrollmentDto>();
		for (Enrollment enrollment : enrollments) {
			EnrollmentDto dto = new EnrollmentDto();
			dto.setRoleId(enrollment.getRoleId());
			dto.setCourseId(enrollment.getCourseId());
			dto.setUserId(enrollment.getUserId());
			dto.setLastEnrolled(enrollment.getLastEnrolled());
			dto.setInCart(enrollment.isInCart());
			dto.setBuy(enrollment.isBuy());
			dtos.add(dto);
		}
		return dtos;
	}

	public EnrollmentDto findDto(int userId, int courseId) {
		EnrollmentDto dto = new EnrollmentDto();
		try {
			Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
			dto.setCourseId(enrollment.getCourseId());
			dto.setUserId(enrollment.getUserId());
			dto.setLastEnrolled(enrollment.getLastEnrolled());
			dto.setInCart(enrollment.isInCart());
			dto.setBuy(enrollment.isBuy());
			System.out.println(dto.toString());

		} catch (Exception e) {
			return null;
		}
		return dto;

	}

	@Override
	public List<CourseDto> findListCourseOfUserInCart(int userId) {
		List<Integer> listCoureId = enrollmentRepository.findCourseIdByUserIdInCart(userId);
		List<CourseDto> dtos = new ArrayList<CourseDto>();
		for (int i = 0; i < listCoureId.size(); i++) {
			int courseId = listCoureId.get(i);
			CourseDto dto = courseService.findDtoById(courseId);
			dtos.add(dto);

		}
		return dtos;
	}

	@Override
	public boolean enroll(int userId, int courseId) {
		try {
//			Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
//			if(enrollment == null)
//				enrollment = new Enrollment();
			Enrollment enrollment = new Enrollment();
			UserDto userDto = userService.findDtoById(userId);
			enrollment.setRoleId(userDto.getRoleId());
			enrollment.setBuy(false);
			enrollment.setCourseId(courseId);
			enrollment.setUserId(userId);
			enrollment.setInCart(true);
			enrollment.setLastEnrolled(new Date());
			enrollmentRepository.saveAndFlush(enrollment);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean buyCourse(int userId, int courseId, double money) {
		try {
//			Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
//			if(enrollment == null)
//				enrollment = new Enrollment();
			userRepository.updateBalance(userId, money);
			Enrollment enrollment = new Enrollment();
			UserDto userDto = userService.findDtoById(userId);
			enrollment.setRoleId(userDto.getRoleId());
			enrollment.setBuy(true);
			enrollment.setCourseId(courseId);
			enrollment.setUserId(userId);
			enrollment.setInCart(false);
			enrollment.setLastEnrolled(new Date());
			enrollmentRepository.saveAndFlush(enrollment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeCart(int userId, int courseId) {
		try {
			enrollmentRepository.setIsInCart(userId, courseId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

	@Override
	public int countEnrollmentInCourse(int id) {

		return enrollmentRepository.countByCourseId(id);
	}

	@Override
	public int coutCourseInCart(int userId) {

		return enrollmentRepository.countCourseOfUserInCart(userId);
	}

	@Override
	public List<CourseDto> findListCourseOfUserIsBuy(int userId) {
		List<Integer> listCoureId = enrollmentRepository.findCourseIdByUserWasBought(userId);
		List<CourseDto> dtos = new ArrayList<CourseDto>();
		for (int i = 0; i < listCoureId.size(); i++) {
			int courseId = listCoureId.get(i);
			CourseDto dto = courseService.findDtoById(courseId);
			dtos.add(dto);

		}
		return dtos;
	}

	public boolean kikAMember(int courseId, int userId) {
		try {
			enrollmentRepository.deleteByUserIdAndCourseId(userId, courseId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<UserDto> findAllMember(int courseId) {
		return enrollmentRepository.findMemberInCourse(courseId);
	}

	public List<UserDto> findAllWhoNotMember(int courseId) {
		List<UserDto> dtos = enrollmentRepository.findMemberNotInCourse(courseId);
		Role role = roleRepository.findFirstByName("ROLE_USER");
		for (int i = 0; i < dtos.size(); i++) {
			if (dtos.get(i).getRoleId() != role.getId())
				dtos.remove(i);
		}
		return dtos;
	}

	@Override
	public boolean addMember(EnrollmentDto dto) {
		try {
//			Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
//			if(enrollment == null)
//				enrollment = new Enrollment();
			Enrollment enrollment = new Enrollment();
			enrollment.setRoleId(dto.getRoleId());
			enrollment.setBuy(true);
			enrollment.setCourseId(dto.getCourseId());
			enrollment.setUserId(dto.getUserId());
			enrollment.setInCart(false);
			enrollment.setLastEnrolled(new Date());
			enrollmentRepository.saveAndFlush(enrollment);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
