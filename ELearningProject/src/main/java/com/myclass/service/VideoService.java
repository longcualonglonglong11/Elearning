package com.myclass.service;

import java.util.List;

import com.myclass.dto.VideoDto;
import com.myclass.entity.Video;

public interface VideoService extends GenericService<Video, Integer>{
	List<VideoDto>findAllDtoByCourseId(int id);
	List<VideoDto> findAllDto();

	boolean addDto(VideoDto dto);

	boolean updateDto(VideoDto dto);

	VideoDto findDtoById(int id);
	
	int sumLengthAllVideoInCourse(int courseId);
	int coutVideoInCourse(int courseId);
}
