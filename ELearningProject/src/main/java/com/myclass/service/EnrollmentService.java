package com.myclass.service;

import java.util.List;

import com.myclass.dto.CourseDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.Enrollment;

public interface EnrollmentService extends GenericService<Enrollment, Integer> {
	boolean enroll(int userId, int courseId);

	List<EnrollmentDto> findAllDto();

	EnrollmentDto findDto(int userId, int courseId);

	List<CourseDto> findListCourseOfUserInCart(int userId);

	List<CourseDto> findListCourseOfUserIsBuy(int userId);

	public boolean buyCourse(int userId, int courseId, double priceOfCourse);

	boolean removeCart(int userId, int courseId);

	int countEnrollmentInCourse(int id);

	int coutCourseInCart(int userId);

	public boolean kikAMember(int courseId, int userId);
	List<UserDto> findAllMember(int courseId);
	List<UserDto> findAllWhoNotMember(int courseId);
	public boolean addMember(EnrollmentDto dto);


}
