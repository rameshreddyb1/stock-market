package com.app.stock.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.stock.entity.User;
import com.app.stock.entity.UserRequest;
import com.app.stock.entity.UserResponse;
import com.app.stock.service.IUserService;
import com.app.stock.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private IUserService service; // HAS-A

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	// Save User data in database
	@PostMapping("/register")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		Integer id = service.saveUser(user);
		return ResponseEntity.ok("User saved with id" + id);
	}

	// Validate user and generate token(login)
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {

		try {
			// Validate username/pwd with database
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));

			String token = jwtUtil.generateToken(userRequest.getUsername());

			return ResponseEntity.ok(new UserResponse(token, "Success! GENERATED BY JMR Infotech"));

		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}

	}

	// After login only
	@PostMapping("/welcome")
	public ResponseEntity<String> accessData(Principal p) {
		return ResponseEntity.ok("Hello User! " + p.getName());
	}

}
