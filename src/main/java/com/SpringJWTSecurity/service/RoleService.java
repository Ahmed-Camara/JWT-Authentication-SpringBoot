package com.SpringJWTSecurity.service;

import java.util.List;

import com.SpringJWTSecurity.model.RoleModel;

public interface RoleService {
	
	public RoleModel createRole(RoleModel roleModel);
	public List<RoleModel> getAllRole();
	public RoleModel getRoleById(Long roleId);
	public void deleteRoleById(Long roleId);
}
