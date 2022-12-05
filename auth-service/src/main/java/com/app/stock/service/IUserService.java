package com.app.stock.service;

import java.util.Optional;

import com.app.stock.entity.User;
import com.app.stock.entity.UserRequest;

public interface IUserService {

	public Integer saveUser(User user);
	public Optional<User> findByUsername(String username);
	public boolean isUserExist(UserRequest userRequest);
}
