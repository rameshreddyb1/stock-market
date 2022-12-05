package com.app.stock.filter;

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

import com.app.stock.config.CustomUserDetailsService;
import com.app.stock.util.JwtUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil util;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Read token from Authorization header
		String token = request.getHeader("Authorization");
		if (token != null) {
			// do validtidation
			String userName = util.getUsername(token);
			//User name should not be empty, context-auth must be empty
			if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails usr = userDetailsService.loadUserByUsername(userName);
				//Validate Token
				util.validateToken(token, usr.getUsername());
				boolean isValid = util.validateToken(token, usr.getUsername());
				if(isValid) {
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(userName, usr.getPassword(),usr.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					//Final object stored in SecurityContext wiht User Details
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}

		logger.info("Successfully authenticated user  ");

		filterChain.doFilter(request, response);
	}
}