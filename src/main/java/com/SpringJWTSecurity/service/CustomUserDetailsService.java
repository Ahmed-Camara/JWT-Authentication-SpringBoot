package com.SpringJWTSecurity.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.SpringJWTSecurity.entity.UserEntity;
import com.SpringJWTSecurity.model.UserModel;
import com.SpringJWTSecurity.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		UserEntity userEntity = userRepository.findByUsername(username);
		
		if(userEntity != null) {
			
			UserModel userModel = new UserModel();
			BeanUtils.copyProperties(userEntity, userModel);
			
			return userModel;
			
		}else {
			throw new UsernameNotFoundException("User does not exist");
		}
	}
	
	public UserModel register(UserModel userModel) {
		UserEntity userEntity = new UserEntity();
		
		BeanUtils.copyProperties(userModel, userEntity);
		
		userEntity.setPassword(this.bCryptPasswordEncoder.encode(userEntity.getPassword()));
		
		userEntity = userRepository.save(userEntity);
		
		BeanUtils.copyProperties(userEntity, userModel);
		
		return userModel;
	}
}
