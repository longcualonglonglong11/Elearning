package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.myclass.dto.RegisterDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.repository.EnrollmentRepository;
import com.myclass.repository.UserRepository;
import com.myclass.service.UserService;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Integer> implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	EnrollmentRepository enrollmentRepository; 
	public List<UserDto> findAllDto() {
		List<User> users = userRepository.findAll();
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User user : users) {
			UserDto dto = new UserDto();
			dto.setId(user.getId());
			dto.setEmail(user.getEmail());
			dto.setFullname(user.getFullname());
			dto.setAddress(user.getAddress());
			dto.setAvatar(user.getAvatar());
			dto.setPhone(user.getPhone());
			dto.setRoleId(user.getRoleId());
			dto.setBalance(user.getBalance());
			dto.setRoleName(user.getRole().getName());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public boolean addDto(UserDto dto) {
		try {
			User user = new User();
			user.setEmail(dto.getEmail());
			user.setPassword(dto.getPassword());
			user.setFullname(dto.getFullname());
			user.setAddress(dto.getAddress());
			user.setAvatar(dto.getAvatar());
			user.setPhone(dto.getPhone());
			user.setRoleId(dto.getRoleId());
			user.setBalance(dto.getBalance());
			System.out.println("RoleID: " + dto.getRoleId());
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDto(UserDto dto) {
		try {
			User user = new User();
			user.setId(dto.getId());
			user.setPhone(dto.getPhone());
			user.setEmail(dto.getEmail());
			user.setPassword(dto.getPassword());
			user.setFullname(dto.getFullname());
			user.setAddress(dto.getAddress());
			user.setAvatar(dto.getAvatar());
			user.setRoleId(dto.getRoleId());
			user.setBalance(dto.getBalance());
			userRepository.saveAndFlush(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateProfile(UserDto dto) {

		try {
			Optional<User> userOp = userRepository.findById(dto.getId());
			User user = userOp.get();
			user.setPhone(dto.getPhone());
			user.setEmail(dto.getEmail());
			user.setFullname(dto.getFullname());
			user.setAddress(dto.getAddress());
			userRepository.saveAndFlush(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateProfilePicture(UserDto dto) {

		try {
			Optional<User> userOp = userRepository.findById(dto.getId());
			User user = userOp.get();
			user.setAvatar(dto.getAvatar());
			userRepository.saveAndFlush(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateSecurity(int id, String password) {
		try {
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt(10));
			userRepository.updatePasswordById(id, hashed);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public UserDto findDtoById(int id) {
		Optional<User> user = userRepository.findById(id);
		UserDto dto = new UserDto();
		dto.setId(id);
		dto.setEmail(user.get().getEmail());
		dto.setFullname(user.get().getFullname());
		dto.setAddress(user.get().getAddress());
		dto.setAvatar(user.get().getAvatar());
		dto.setRoleId(user.get().getRoleId());
		dto.setRoleName(user.get().getRole().getName());
		dto.setPhone(user.get().getPhone());
		dto.setPassword(user.get().getPassword());
		dto.setBalance(user.get().getBalance());
		return dto;
	}

	public List<UserDto> findDtoByAllLecturer() {
		return userRepository.findDtoByRoleName("ROLE_LECTURER");
	}

	public boolean authorizeToCoHost(int userId, int courseId) {
		try {
			userRepository.updatedNewRole(userId, "ROLE_SUB_LECTURER");
			enrollmentRepository.updatedNewRole(userId, courseId, "ROLE_SUB_LECTURER");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}
	public boolean cancleToMember(int userId, int courseId) {
		try {
			userRepository.updatedNewRole(userId, "ROLE_USER");
			enrollmentRepository.updatedNewRole(userId, courseId, "ROLE_USER");

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}
}
