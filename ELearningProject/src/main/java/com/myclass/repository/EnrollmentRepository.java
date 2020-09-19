package com.myclass.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.Enrollment;
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer>{
	@Query("SELECT e.courseId FROM Enrollment e WHERE e.userId = ?1 AND e.isInCart = TRUE")
	List<Integer> findCourseIdByUserIdInCart(int userId);
	@Query("SELECT e.courseId FROM Enrollment e WHERE e.userId = ?1 AND e.isBuy = TRUE")
	List<Integer> findCourseIdByUserWasBought(int userId);
	Enrollment findByUserIdAndCourseId(int userId, int courseId);
	@Transactional
	@Modifying
	@Query("UPDATE Enrollment SET isInCart = 0 WHERE userId = ?1 AND courseId = ?2")
	void setIsInCart(int userId, int courseId);
	@Query("SELECT COUNT(e.courseId) FROM Enrollment e WHERE e.courseId = ?1 AND e.isBuy = TRUE")
	int countByCourseId(int id);
	@Query("SELECT COUNT(e.courseId) FROM Enrollment e WHERE e.userId = ?1 AND e.isInCart = TRUE")
	int countCourseOfUserInCart(int userId);
	@Transactional
	void deleteByUserIdAndCourseId(int userId, int courseId);
	
	@Query("SELECT new com.myclass.dto.UserDto(u.id, u.email, u.fullname, u.avatar, u.phone, u.address, u.role) FROM User u\r\n" + 
			"INNER JOIN Enrollment uc\r\n" + 
			"ON u.id = uc.userId\r\n" + 
			"WHERE uc.courseId = ?1")
	List<UserDto> findMemberInCourse(int courseId);
	@Query("SELECT new com.myclass.dto.UserDto(u1.id, u1.email, u1.fullname, u1.avatar, u1.phone, u1.address, u1.role) FROM User u1\r\n" + 
			"WHERE u1.id NOT IN\r\n" + 
			"(\r\n" + 
			"SELECT u2.id FROM User u2\r\n" + 
			"INNER JOIN Enrollment uc\r\n" + 
			"ON u2.id = uc.userId\r\n" + 
			"WHERE uc.courseId = ?1\r\n" + 
			")")
	List<UserDto> findMemberNotInCourse(int courseId);
	@Transactional
	@Modifying
	@Query("UPDATE Enrollment u \r\n" + 
			"SET u.roleId = (SELECT r.id FROM Role r WHERE r.name = ?3)\n" + 
			"WHERE u.userId = ?1 AND u.courseId = ?2\n")
	void updatedNewRole(int userId, int courseId, String roleName);
	@Query("SELECT uc FROM Enrollment uc\n" +
			"WHERE uc.isBuy = 1"+
			"GROUP BY uc.courseId\n" + 
			"ORDER BY COUNT(uc.userId) DESC")
	List<Enrollment> findTopSellerCourseId(Pageable pageable);

}
