package com.SpringJWTSecurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.SpringJWTSecurity.service.CustomUserDetailsService;
import com.SpringJWTSecurity.utils.JwtUtils;

//OncePerRequestFilter ensures that this class filter is goint to be called only once per request.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	// this method will be called before any controller method endpoint is called.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// get jwt token from request header
		// validate the jwt token
		//get token value from the http servlet authorization header
		String bearerToken = request.getHeader("Authorization");
		String username = null;
		String token = null;
		
		// check if token existe or has Bearer text.
		if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
			//extract jwt token from bearerToken
			token = bearerToken.substring(7);
			
			try {
				
				//extract username from the token
				username = jwtUtils.extractUsername(token);
				
				//get user details for this user.
				UserDetails user = customUserDetailsService.loadUserByUsername(username);
				
				// security checks
				if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					
					UsernamePasswordAuthenticationToken updt =
							new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
					
					updt.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(updt);
					
				}else {
					
					System.out.println("Invalid Token Format");
				}
				
			}catch(Exception ex) {
				
				System.out.println(ex.getMessage());
			}
		
		}else {
			System.out.println("Invalid Bearer Token Format");
		}
		
		// if user authenticated, then forward the request to the request endpoint.
		filterChain.doFilter(request, response);
		
	}
	
	

}
