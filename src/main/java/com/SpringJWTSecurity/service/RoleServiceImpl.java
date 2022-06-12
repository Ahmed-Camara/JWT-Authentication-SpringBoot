package com.SpringJWTSecurity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringJWTSecurity.entity.RoleEntity;
import com.SpringJWTSecurity.model.RoleModel;
import com.SpringJWTSecurity.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public RoleModel createRole(RoleModel roleModel) {
		
		RoleEntity roleEntity = new RoleEntity();
		BeanUtils.copyProperties(roleModel, roleEntity);
		roleEntity = roleRepository.save(roleEntity);
		
		BeanUtils.copyProperties(roleEntity, roleModel);;
		
		return roleModel;
	}

	@Override
	public List<RoleModel> getAllRole() {
		List<RoleEntity> roleEntities = roleRepository.findAll();
		List<RoleModel> roleModels = new ArrayList<>();
		
		RoleModel roleMdodel = null;
		
		for(RoleEntity re : roleEntities) {
			
			roleMdodel = new RoleModel();
			BeanUtils.copyProperties(re, roleMdodel);
			
			roleModels.add(roleMdodel);
		}
		
		return roleModels;
	}

	@Override
	public RoleModel getRoleById(Long roleId) {
		RoleEntity roleEntity = roleRepository.findById(roleId).get();
		
		RoleModel roleModel = new RoleModel();
		
		BeanUtils.copyProperties(roleEntity, roleModel);
		
		return roleModel;
	}

}
