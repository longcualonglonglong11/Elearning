package com.myclass.service;

import java.util.List;

import com.myclass.dto.CategoryDto;
import com.myclass.dto.CourseDto;
import com.myclass.dto.HomeCourseDto;
import com.myclass.entity.Course;

public interface CourseService extends GenericService<Course, Integer> {
	List<CourseDto> findAllDto();

	boolean addDto(CourseDto dto);

	boolean updateDto(CourseDto dto);

	CourseDto findDtoById(int id);

	public List<HomeCourseDto> findAllHomeCouseDtoTopSeller(int top);

	public List<HomeCourseDto> findAllHomeCouseDtoOnSale(int top);

	CategoryDto findCategoryByCourseId(int courseId);

	List<CourseDto> findAllDtoByCategoryId(int categoryId);

	List<CourseDto> searchCourseByTitle(String title);

	public List<CourseDto> findAllCourseOfLecturer(String author);

	public CourseDto findAllCourseOfSubLecturer(int id);

	public List<HomeCourseDto> findAllFreeHomeCouseDtoTopSeller(int top);

	public List<HomeCourseDto> findAllChargedHomeCouseDtoTopSeller(int top);

}
