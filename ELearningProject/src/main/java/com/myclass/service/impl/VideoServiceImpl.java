package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.VideoDto;
import com.myclass.entity.Video;
import com.myclass.entity.Video;
import com.myclass.repository.VideoRepository;
import com.myclass.service.VideoService;
@Service
public class VideoServiceImpl extends GenericServiceImpl<Video, Integer> implements VideoService{

	@Autowired
	VideoRepository videoRepository;

	@Override
	public List<VideoDto> findAllDtoByCourseId(int id) {
		List<Video> videos = videoRepository.findByCourseId(id);
		List<VideoDto> dtos = new ArrayList<VideoDto>();
		for (Video video : videos) {
			VideoDto dto = new VideoDto();
			dto.setId(video.getId());
			dto.setTitle(video.getTitle());
			dto.setUrl(video.getUrl());
			dto.setLength(video.getLength());
			dto.setTimeCount(video.getTimeCount());
			dto.setCourseId(video.getCourseId());
			dto.setCourseName(video.getCourse().getTitle());
			dtos.add(dto);
		}
		return dtos;
	}
	public List<VideoDto> findAllDto() {
		List<Video> videos = videoRepository.findAll();
		List<VideoDto> dtos = new ArrayList<VideoDto>();
		for (Video video : videos) {
			VideoDto dto = new VideoDto();
			dto.setId(video.getId());
			dto.setTitle(video.getTitle());
			dto.setUrl(video.getUrl());
			dto.setLength(video.getLength());
			dto.setTimeCount(video.getTimeCount());
			dto.setCourseId(video.getCourseId());
			dto.setCourseName(video.getCourse().getTitle());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public boolean addDto(VideoDto dto) {
		try {
			Video video = new Video();
			video.setTitle(dto.getTitle());
			video.setUrl(dto.getUrl());
			video.setLength(dto.getLength());
			video.setCourseId(dto.getCourseId());
			videoRepository.save(video);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDto(VideoDto dto) {
		try {
			Video video = new Video();
			video.setId(dto.getId());
			video.setTitle(dto.getTitle());
			video.setUrl(dto.getUrl());
			video.setLength(dto.getLength());
			video.setTimeCount(dto.getTimeCount());
			video.setCourseId(dto.getCourseId());
			videoRepository.saveAndFlush(video);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public VideoDto findDtoById(int id) {
		Optional<Video> video = videoRepository.findById(id);
		VideoDto dto = new VideoDto();
		dto.setId(id);
		dto.setTitle(video.get().getTitle());
		dto.setCourseId(video.get().getCourseId());
		dto.setUrl(video.get().getUrl());
		dto.setLength(video.get().getLength());
		dto.setTimeCount(video.get().getTimeCount());
		dto.setCourseName(video.get().getCourse().getTitle());
		return dto;
	}
	@Override
	public int sumLengthAllVideoInCourse(int courseId) {
		int integer = videoRepository.sumLenghtByCourseId(courseId);
		return videoRepository.sumLenghtByCourseId(courseId);
	}
	@Override
	public int coutVideoInCourse(int courseId) {
		
		return videoRepository.countByCourseId(courseId);
	}
}
