package com.myclass.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.entity.Category;
import com.myclass.entity.Target;

public class CourseDto {
	
	private int id;
	@NotBlank(message = "Title can not be blank !")
	private String title;
	private String image;
	
	
	@Min(value = 0)
	private float price;
	@Min(value = 0)
	private float discount;	
	private int categoryId;

	@NotBlank(message = "Description can not be blank !")
	private String description;
	@NotBlank(message = "Content can not be blank !")
	private String content;
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastUpdate;
	private int lectureCount;
	private int targetCount;
	private int lengthVideos;
	private int viewCount;
	private String categoryName;
	private String author;

	private MultipartFile[] fileDatas;
	public CourseDto() {}
	public CourseDto(int id, @NotBlank(message = "Title can not be blank !") String title,
			@NotBlank(message = "Image can not be blank !") String image) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
	}
	
	public CourseDto(int id, String title, String image,
			float price,  String description,
			String author) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.price = price;
		this.description = description;
		this.author = author;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getDiscount() {
		return discount;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLectureCount() {
		return lectureCount;
	}

	public void setLectureCount(int lectureCount) {
		this.lectureCount = lectureCount;
	}



	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getTargetCount() {
		return targetCount;
	}

	public void setTargetCount(int targetCount) {
		this.targetCount = targetCount;
	}

	public int getLengthVideos() {
		return lengthVideos;
	}

	public void setLengthVideos(int lengthVideos) {
		this.lengthVideos = lengthVideos;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public MultipartFile[] getFileDatas() {
		return fileDatas;
	}
	public void setFileDatas(MultipartFile[] fileDatas) {
		this.fileDatas = fileDatas;
	}
	@Override
	public String toString() {
		return "CourseDto [id=" + id + ", title=" + title + ", image=" + image + ", price=" + price + ", discount="
				+ discount + ", categoryId=" + categoryId + ", description=" + description + ", content=" + content
				+ ", lastUpdate=" + lastUpdate + ", lectureCount=" + lectureCount + ", targetCount=" + targetCount
				+ ", lengthVideos=" + lengthVideos + ", viewCount=" + viewCount + ", categoryName=" + categoryName
				+ ", author=" + author + ", fileDatas=" + Arrays.toString(fileDatas) + "]";
	}

	
	
}
