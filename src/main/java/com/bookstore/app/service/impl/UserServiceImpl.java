package com.bookstore.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.app.dto.UserDto;
import com.bookstore.app.model.User;
import com.bookstore.app.repository.UserRepository;
import com.bookstore.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return userRepository.login(username, password);
	}

	@Override
	public User addNewUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setIsAdmin(userDto.getIsAdmin());
		return userRepository.save(user);
	}

}
