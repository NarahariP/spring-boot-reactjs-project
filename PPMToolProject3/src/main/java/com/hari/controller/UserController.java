package com.hari.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hari.model.User;
import com.hari.payload.JWTLoginSuccessResponse;
import com.hari.payload.LoginRequest;
import com.hari.security.JwtTokenProvider;
import com.hari.security.SecurityConstants;
import com.hari.service.UserService;
import com.hari.utils.ProjectUtils;
import com.hari.validator.UserValidator;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> aunticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		if (ProjectUtils.bindingResultFunction.apply(result).size() != 0) {
			return new ResponseEntity<>(ProjectUtils.bindingResultFunction.apply(result), HttpStatus.BAD_REQUEST);
		}
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));

	}

	@PostMapping("/register")
	private ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
		System.out.println(user.toString() + "In");
		userValidator.validate(user, result);
		if (ProjectUtils.bindingResultFunction.apply(result).size() != 0) {
			return new ResponseEntity<>(ProjectUtils.bindingResultFunction.apply(result), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
	}

	@GetMapping("")
	private ResponseEntity<User> retriveUser(Principal principal) {
		System.out.println("okkk " + principal.getName());
		final User user = userService.retriveUser(principal.getName());
		System.out.println(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
