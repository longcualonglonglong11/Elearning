package com.myclass.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.dto.CourseDto;
import com.myclass.entity.Category;
import com.myclass.entity.Course;
import com.myclass.entity.Enrollment;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
	List<Course> findByCategoryId(int categoryId);

	List<Course> findByTitleContaining(String title);

	@Query("SELECT new com.myclass.dto.CourseDto(u.id, u.title, u.image) FROM Course u WHERE u.image =:image")
	public CourseDto findFileByName(@Param("image") String image);
	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image,  c.promotionPrice, c.description, c.author) FROM Course c\r\n" + 
			"INNER JOIN Enrollment uc\r\n" + 
			"ON uc.courseId = c.id\r\n" + 
			"AND uc.userId = ?1\n" + 
			"WHERE uc.roleId = ?2")
	public CourseDto findACourseDtoByUserIdAndRoleId(int id, int roleId);

	@Query("SELECT new com.myclass.dto.CourseDto(u.id, u.title, u.image,  u.promotionPrice, u.description, u.author) FROM Course u WHERE u.author = ?1")
	public List<CourseDto> findAllCourseDtoByAuthorName(String author);
	@Query("SELECT u FROM Course u ORDER BY u.discount DESC")
	public List<Course> findTopCourseOnSale(Pageable pageable);
	@Query("SELECT c FROM Course c WHERE EXISTS (\r\n" + 
			"SELECT uc.courseId FROM Enrollment uc\r\n" + 
			"WHERE c.id = uc.courseId\r\n" + 
			"GROUP BY uc.courseId\r\n" + 
			"order by COUNT(uc.courseId) DESC\r\n" + 
			"\r\n" + 
			")")
	public List<Course> findBestSellerCourse(Pageable pageable);
}
