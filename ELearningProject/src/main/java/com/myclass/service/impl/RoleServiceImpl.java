package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.RoleDto;
import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;
import com.myclass.service.RoleService;

@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, Integer> implements RoleService{
	@Autowired
	RoleRepository roleRepository;
	
	public List<RoleDto> findAllDto() {
		List<Role> roles = roleRepository.findAll();
		List<RoleDto> dtos = new ArrayList<RoleDto>();
		for (Role role : roles) {
			RoleDto dto = new RoleDto();
			dto.setId(role.getId());
			dto.setName(role.getName());
			dto.setDescription(role.getDescription());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public boolean addDto(RoleDto dto) {
		try {
			Role role = new Role();
			role.setName(dto.getName());
			role.setDescription(dto.getDescription());
			roleRepository.save(role);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDto(RoleDto dto) {
		try {
			Role role = new Role();
			role.setId(dto.getId());
			role.setName(dto.getName());
			role.setDescription(dto.getDescription());
			roleRepository.saveAndFlush(role);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public RoleDto findDtoById(int id) {
		Optional<Role> role = roleRepository.findById(id);
		RoleDto dto = new RoleDto();
		dto.setId(id);
		dto.setName(role.get().getName());
		dto.setDescription(role.get().getDescription());
		return dto;
	}
	@Override
	public RoleDto findDtoByName(String name) {
		Role role = roleRepository.findFirstByName(name);
		RoleDto dto = new RoleDto();
		dto.setId(role.getId());
		dto.setName(role.getName());
		dto.setDescription(role.getDescription());
		return dto;
	}
}
