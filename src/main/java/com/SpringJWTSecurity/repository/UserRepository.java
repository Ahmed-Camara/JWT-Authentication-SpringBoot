package com.SpringJWTSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringJWTSecurity.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{

}
