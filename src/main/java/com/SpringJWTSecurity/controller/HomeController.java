package com.SpringJWTSecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/hello")
	public String home() {
		return "Hello from homeController";
	}
	
	@GetMapping("/hl")
	public String test(){
		return  "second Hello from homeController";
	}
	
	
}
