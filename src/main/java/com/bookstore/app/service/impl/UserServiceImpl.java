package com.bookstore.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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
		return userRepository.login(username, passwordEncoder.encode(password));
	}

	@Override
	public User addNewUser(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setIsAdmin(userDto.getIsAdmin());
		user.setIsActive(userDto.getIsActive());
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			throw new DuplicateKeyException("Duplicate entry: " + userDto.getUsername());
		}
	}

}
