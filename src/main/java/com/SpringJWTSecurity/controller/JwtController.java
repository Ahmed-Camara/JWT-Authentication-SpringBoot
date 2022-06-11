package com.SpringJWTSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringJWTSecurity.model.JwtRequest;
import com.SpringJWTSecurity.model.JwtResponse;
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
	
	@PostMapping("/generateToken")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){
		System.out.println("Welcome");
		
		UsernamePasswordAuthenticationToken upat = 
				new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword());
		authenticationManager.authenticate(upat);
		
		UserDetails udt = customerUserDetailService.loadUserByUsername(jwtRequest.getUsername());
		
		String token = jwtUtils.generateToken(udt);
		
		JwtResponse jwtResponse = new JwtResponse(token);
		
		System.out.println("The token generated is : "+token);
		
		return ResponseEntity.ok(jwtResponse);
	}
}
