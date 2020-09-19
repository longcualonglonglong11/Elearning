package com.myclass.service;

import java.util.List;

import com.myclass.dto.RoleDto;
import com.myclass.entity.Role;

public interface RoleService extends GenericService<Role, Integer> {
	List<RoleDto> findAllDto();
	boolean addDto(RoleDto dto);
	boolean updateDto(RoleDto dto);
	RoleDto findDtoById(int id);
	RoleDto findDtoByName(String name);
}
