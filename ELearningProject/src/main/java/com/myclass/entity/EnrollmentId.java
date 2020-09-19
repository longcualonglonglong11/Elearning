package com.myclass.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class EnrollmentId implements Serializable {
	private int userId;

	private int courseId;
}
