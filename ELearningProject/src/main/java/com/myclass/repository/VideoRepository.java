package com.myclass.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myclass.entity.Video;
@Repository
public interface VideoRepository extends JpaRepository<Video, Integer>{
	int countByCourseId(int id);
	@Query("SELECT SUM(length) FROM Video WHERE courseId = ?1")
	int sumLenghtByCourseId(int id);

	List<Video>findByCourseId(int id);
}
