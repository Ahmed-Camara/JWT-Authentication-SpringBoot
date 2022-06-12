package com.SpringJWTSecurity.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringJWTSecurity.model.JwtRequest;
import com.SpringJWTSecurity.model.JwtResponse;
import com.SpringJWTSecurity.model.UserModel;
import com.SpringJWTSecurity.service.CustomUserDetailsService;
import com.SpringJWTSecurity.utils.JwtUtils;

@RestController
@RequestMapping("/api")
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private CustomUserDetailsService customerUserDetailService;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){
		
		UsernamePasswordAuthenticationToken upat = 
				new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword());
		
		authenticationManager.authenticate(upat);
		
		UserDetails udt = customerUserDetailService.loadUserByUsername(jwtRequest.getUsername());
		
		String token = jwtUtils.generateToken(udt);
		
		JwtResponse jwtResponse = new JwtResponse(token);
		
		return ResponseEntity.ok(jwtResponse);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserModel> register(@RequestBody UserModel userModel){
		
		UserModel user =customerUserDetailService.register(userModel);
		ResponseEntity<UserModel> re = new ResponseEntity<>(user,HttpStatus.CREATED);
		
		return re;
	}
	
	@GetMapping("/current-user")
	public UserModel getCurrentUser(Principal principal) {
		
		UserDetails userDetails = this.customerUserDetailService.loadUserByUsername(principal.getName());
		
		return (UserModel) userDetails;
	}
}
