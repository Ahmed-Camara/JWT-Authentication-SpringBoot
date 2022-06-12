package com.SpringJWTSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringJWTSecurity.entity.RoleEntity;
import com.SpringJWTSecurity.model.RoleModel;
import com.SpringJWTSecurity.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/roles")
	public RoleModel createRole(@RequestBody RoleModel roleModel) {
		
		return roleService.createRole(roleModel);
	}
	
	@GetMapping("/roles")
	public List<RoleModel> getAllRoles(){
		return roleService.getAllRole();
	}
}
