package com.myclass.service;

import java.util.List;

import com.myclass.dto.RegisterDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

public interface UserService extends GenericService<User, Integer> {
	List<UserDto> findAllDto();

	boolean addDto(UserDto dto);

	boolean updateDto(UserDto dto);

	UserDto findDtoById(int id);

	boolean updateProfile(UserDto dto);

	public boolean updateProfilePicture(UserDto dto);
	public boolean updateSecurity(int id, String password) ;
	public List<UserDto> findDtoByAllLecturer();
	public boolean authorizeToCoHost(int userId, int courseId);
	public boolean cancleToMember(int userId, int courseId);

}
