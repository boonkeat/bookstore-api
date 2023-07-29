package com.bookstore.app.controller;

import com.bookstore.app.dto.LoginDto;
import com.bookstore.app.dto.UserDto;
import com.bookstore.app.dto.UserInDto;
import com.bookstore.app.jwt.JwtTokenUtil;
import com.bookstore.app.model.User;
import com.bookstore.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value="/bookstore/user",
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE},
		produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> retrieve(@RequestBody UserInDto userInDto, HttpServletRequest request) {
		if(null == userInDto && !StringUtils.hasText(userInDto.getUsername()) && !StringUtils.hasText(userInDto.getPassword())) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							userInDto.getUsername(), userInDto.getPassword())
			);
			System.out.println("Login parameters ---> " + userInDto);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtUtil.generateToken(authentication);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", "Bearer " + token);

			return new ResponseEntity<>(new LoginDto(token),httpHeaders, HttpStatus.OK);

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}



//		try {
//			request.login(userInDto.getUsername(), userInDto.getPassword());
//		} catch (ServletException e) {
//			throw new RestApiException("Invalid username or password", HttpStatus.UNAUTHORIZED);
//		}
//
//		var auth = (Authentication) request.getUserPrincipal();
//		var loggedInUser = (User) auth.getPrincipal();
//		log.info("User {} logged in.", loggedInUser.getUsername());

//		if(loggedInUser != null) {
//			System.out.println("User is found ---> " + loggedInUser);
//			String jwtToken = Jwts.builder()
//					.claim("id", loggedInUser.getId())
//					.claim("username", loggedInUser.getUsername())
//			        .claim("isAdmin", loggedInUser.getIsAdmin())
//					.compact();
//			return new ResponseEntity<>(jwtToken, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
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
