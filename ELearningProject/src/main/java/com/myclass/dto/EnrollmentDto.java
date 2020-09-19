package com.myclass.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class EnrollmentDto {

	private int userId;

	private int courseId;

	private int roleId;

	Date lastEnrolled;

	boolean isInCart;

	boolean isBuy;

	String courseName;
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Date getLastEnrolled() {
		return lastEnrolled;
	}

	public void setLastEnrolled(Date lastEnrolled) {
		this.lastEnrolled = lastEnrolled;
	}

	public boolean isInCart() {
		return isInCart;
	}

	public void setInCart(boolean isInCart) {
		this.isInCart = isInCart;
	}

	public boolean isBuy() {
		return isBuy;
	}

	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "EnrollmentDto [userId=" + userId + ", courseId=" + courseId + ", roleId=" + roleId + ", lastEnrolled="
				+ lastEnrolled + ", isInCart=" + isInCart + ", isBuy=" + isBuy + "]";
	}
	
}
