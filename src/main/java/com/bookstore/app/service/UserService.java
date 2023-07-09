package com.bookstore.app.service;

import com.bookstore.app.dto.UserDto;
import com.bookstore.app.model.User;

public interface UserService {
	User login(String username, String password);
	
	User addNewUser(UserDto userDto);
}
