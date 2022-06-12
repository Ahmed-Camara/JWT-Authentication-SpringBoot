package com.SpringJWTSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringJWTSecurity.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

}
