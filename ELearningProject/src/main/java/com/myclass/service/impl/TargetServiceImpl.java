package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.TargetDto;
import com.myclass.entity.Target;
import com.myclass.entity.Target;
import com.myclass.entity.Video;
import com.myclass.repository.TargetRepository;
import com.myclass.repository.VideoRepository;
import com.myclass.service.TargetService;
@Service
public class TargetServiceImpl extends GenericServiceImpl<Target, Integer> implements TargetService{
	@Autowired
	TargetRepository targetRepository;
	@Override
	public List<TargetDto> findAllDtoByCourseId(int id) {
		List<Target> targets = targetRepository.findByCourseId(id);
		List<TargetDto> dtos = new ArrayList<TargetDto>();
		for (Target target : targets) {
			TargetDto dto = new TargetDto();
			dto.setId(target.getId());
			dto.setTitle(target.getTitle());
			dto.setCourseId(target.getCourseId());
			dto.setCourseName(target.getCourse().getTitle());
			dtos.add(dto);
		}
		return dtos;
	}
	public List<TargetDto> findAllDto() {
		List<Target> targets = targetRepository.findAll();
		List<TargetDto> dtos = new ArrayList<TargetDto>();
		for (Target target : targets) {
			TargetDto dto = new TargetDto();
			dto.setId(target.getId());
			dto.setTitle(target.getTitle());
			dto.setCourseId(target.getCourseId());
			dto.setCourseName(target.getCourse().getTitle());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public boolean addDto(TargetDto dto) {
		try {
			Target target = new Target();
			target.setTitle(dto.getTitle());
			target.setCourseId(dto.getCourseId());
			targetRepository.save(target);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDto(TargetDto dto) {
		try {
			Target target = new Target();
			target.setId(dto.getId());
			target.setTitle(dto.getTitle());
			target.setCourseId(dto.getCourseId());
			targetRepository.saveAndFlush(target);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public TargetDto findDtoById(int id) {
		Optional<Target> target = targetRepository.findById(id);
		TargetDto dto = new TargetDto();
		dto.setId(id);
		dto.setTitle(target.get().getTitle());
		dto.setCourseId(target.get().getCourseId());
		dto.setCourseName(target.get().getCourse().getTitle());
		return dto;
	}
	

}
