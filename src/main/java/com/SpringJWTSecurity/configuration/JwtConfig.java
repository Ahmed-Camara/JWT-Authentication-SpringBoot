package com.SpringJWTSecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.SpringJWTSecurity.filter.JwtAuthenticationFilter;
import com.SpringJWTSecurity.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class JwtConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	JwtAuthenticationFilter jwtFilter;
	
	@Autowired
	private  CustomUserDetailsService customUserDetailsService;
	// We can control what will be our authentication mode.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(customUserDetailsService);
	}
	
	// with this method the method, we will control the endpoints admitted or not admitted
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http
			.csrf()
			.disable()
			.cors()
			.disable()
			.authorizeRequests()
			.antMatchers("/api/generateToken")
			.permitAll() // this only allow /generateToken without authentication
			.and()
			.authorizeRequests()
			.antMatchers("/api/roles").permitAll()
			.and()
			.authorizeRequests()
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest() // after that any other request should be authenticated
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // request should be stateless which means server does not have to track requests
		
		
		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		// should not be used in production
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
}
