package com.app.stock.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.stock.entity.User;
import com.app.stock.entity.UserRequest;
import com.app.stock.repository.UserRepository;
import com.app.stock.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Transactional
	public Integer saveUser(User user) {
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		return repository.save(user).getId();
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public boolean isUserExist(UserRequest userRequest) {
		Optional<User> user = findByUsername(userRequest.getUsername());
		if(user.get().getPassword().equals(userRequest.getPassword()))
			return true;
		else 
			return false;
	}
	
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		Optional<User> opt = findByUsername(userName);
		if(opt.isEmpty())
			throw new UsernameNotFoundException("User Not Exist");
		
		//Read user from DB
		User user = opt.get();
		
		return new org.springframework.security.core.userdetails.User(
				userName, 
				user.getPassword(), 
				user.getRoles().stream()
				.map(role->new SimpleGrantedAuthority(role))
				.collect(Collectors.toList())
				);
	}

}
