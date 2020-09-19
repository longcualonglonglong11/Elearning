package com.myclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.myclass.dto.UserDto;
import com.myclass.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	@Query("SELECT new com.myclass.dto.UserDto(u.id, u.fullname, u.avatar) FROM com.myclass.entity.User u WHERE u.avatar =:avatar")
	public UserDto findFileByAvatarName(@Param("avatar") String avatar);
	@Transactional
	@Modifying
	@Query("UPDATE User SET password = ?2 WHERE id = ?1")
	void updatePasswordById(int id, String password);
	public User findByEmail(String email);
	@Query("SELECT new com.myclass.dto.UserDto(u.id, u.fullname, u.avatar) FROM com.myclass.entity.User u INNER JOIN Role r \n" + 
			"	ON u.roleId = r.id\n" + 
			"	WHERE r.name = ?1\n")
	public List<UserDto> findDtoByRoleName(String roleName);
	@Transactional
	@Modifying
	@Query("UPDATE User u \r\n" + 
			"SET u.roleId = (SELECT r.id FROM Role r WHERE r.name = ?2)\n" + 
			"WHERE u.id = ?1\n")
	void updatedNewRole(int id, String roleName);
	@Transactional
	@Modifying
	@Query("UPDATE User u \r\n" + 
			"SET u.balance = ?2\n" + 
			"WHERE u.id = ?1\n")
	void updateBalance(int id, double money);

}
