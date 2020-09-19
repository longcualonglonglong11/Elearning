package com.myclass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.myclass.dto.RegisterDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;
import com.myclass.service.RegisterService;
import com.myclass.service.UserService;
@Service
public class RegisterServiceImpl implements RegisterService{
	@Autowired
	UserRepository userRepository;
	@Override
	public boolean register(RegisterDto dto) {
		try {

			User user = new User();
			user.setEmail(dto.getEmail());
			String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10));

			user.setPassword(hashed);
			user.setFullname("New User");
			user.setRoleId(2);
			user.setAvatar("https://vnn-imgs-a1.vgcloud.vn/image1.ictnews.vn/_Files/2020/03/17/trend-avatar-1.jpg");
			user.setBalance(100);
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
