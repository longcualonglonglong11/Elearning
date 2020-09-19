package com.myclass.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author Long
 *
 */
public class LoginDto extends User implements UserDetails {

	/**
	 * 
	 */
	private int id;
	private String fullname;
	private String avatar;
	private String password;
	private double balance;
	int roleId;
	private static final long serialVersionUID = 1L;

	public LoginDto(String email, String password, Collection<? extends GrantedAuthority> authorities, int id, String fullname, String avatar, double balance, int roleId) {
		super(email, password, authorities);
		this.id = id;
		this.fullname = fullname;
		this.avatar = avatar;
		this.password = password;
		this.roleId = roleId;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	

}
