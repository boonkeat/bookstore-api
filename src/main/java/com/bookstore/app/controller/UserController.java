package com.bookstore.app.controller;

import com.bookstore.app.dto.UserDto;
import com.bookstore.app.dto.UserInDto;
import com.bookstore.app.model.User;
import com.bookstore.app.service.UserService;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/bookstore/user",
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE},
		produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<String> retrieve(@RequestBody UserInDto userInDto) {
		if(null == userInDto && !StringUtils.hasText(userInDto.getUsername()) && !StringUtils.hasText(userInDto.getPassword())) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		System.out.println("Login parameters ---> " + userInDto);
		User user = userService.login(userInDto.getUsername(), userInDto.getPassword());

		if(user != null) {
			System.out.println("User is found ---> " + user);
			String jwtToken = Jwts.builder()
					.claim("userId", user.getId())
					.claim("username", user.getUsername())
					.claim("isAdmin", user.getIsAdmin())
					.compact();
			return new ResponseEntity<>(jwtToken, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Long> add(@Valid @RequestBody UserDto userDto) {
		System.out.println("User add parameters ---> " + userDto.toString());
		User user = userService.addNewUser(userDto);
		if(null != user) {
			return new ResponseEntity<>(user.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
