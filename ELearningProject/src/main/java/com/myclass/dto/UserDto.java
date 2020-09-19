package com.myclass.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.myclass.entity.Role;
import com.sun.istack.NotNull;

public class UserDto {

	private int id;
	@NotBlank(message = "Email can not be blank")

	private String email;
	@NotBlank(message = "Fullname can not be blank")

	private String fullname;
	@NotBlank(message = "Password can not be blank")
	private String password;
//	@NotBlank(message = "Confirm password can not be blank")
	private String confirmPassword;
	private String avatar;
	@NotBlank(message = "Phone can not be blank")

	private String phone;
	@NotBlank(message = "Address can not be blank")

	private String address;
	private int roleId;
	private String roleName;
	@Min(value = 0, message = "Balace can't be nagative")
	private double balance;
	private MultipartFile[] fileDatas;

	public UserDto(int id, String email, String fullname, String avatar, String phone, String address) {
		this.id = id;
		this.email = email;
		this.fullname = fullname;
		this.avatar = avatar;
		this.phone = phone;
		this.address = address;
	}
	public UserDto(int id, String email, String fullname, String avatar, String phone, String address, Role role) {
		this.id = id;
		this.email = email;
		this.fullname = fullname;
		this.avatar = avatar;
		this.phone = phone;
		this.address = address;
		this.roleName = role.getDescription();
		this.roleId =  role.getId();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public UserDto() {

	}

	public UserDto(int id, @NotBlank(message = "Fullname can not be blank") String fullname, String avatar) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.avatar = avatar;
	}

	public MultipartFile[] getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(MultipartFile[] fileDatas) {
		this.fileDatas = fileDatas;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
