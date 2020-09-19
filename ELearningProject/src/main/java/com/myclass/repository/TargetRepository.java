package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myclass.entity.Target;
import com.myclass.entity.Video;
@Repository
public interface TargetRepository extends JpaRepository<Target, Integer>{
	int countByCourseId(int id);
	List<Target>findByCourseId(int id);

}
