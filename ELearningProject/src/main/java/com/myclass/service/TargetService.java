package com.myclass.service;

import java.util.List;

import com.myclass.dto.TargetDto;
import com.myclass.entity.Target;
import com.myclass.entity.Video;

public interface TargetService extends GenericService<Target, Integer> {

	List<TargetDto> findAllDto();

	boolean addDto(TargetDto dto);

	boolean updateDto(TargetDto dto);

	TargetDto findDtoById(int id);

	List<TargetDto> findAllDtoByCourseId(int id);

}
