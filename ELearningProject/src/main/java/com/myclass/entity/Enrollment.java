package com.myclass.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Transactional
@Table(name = "user_courses")
@IdClass(EnrollmentId.class)
public class Enrollment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "user_id")
	private int userId;
	@Id
	@Column(name = "course_id")

	private int courseId;
	@Column(name = "role_id")

	private int roleId;
	@Column(name = "last_enrolled")

	Date lastEnrolled;
	@Column(name = "is_in_cart")

	boolean isInCart;
	@Column(name = "is_buy")

	boolean isBuy;
	
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
	
	public boolean isBuy() {
		return isBuy;
	}
	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}
	public boolean isInCart() {
		return isInCart;
	}
	public void setInCart(boolean isInCart) {
		this.isInCart = isInCart;
	}
	@Override
	public String toString() {
		return "Enrollment [userId=" + userId + ", courseId=" + courseId + ", roleId=" + roleId + ", lastEnrolled="
				+ lastEnrolled + ", isInCart=" + isInCart + ", isBuy=" + isBuy + "]";
	}
	
	
}
