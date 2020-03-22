package com.hari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hari.exception.UsernameAlreadyExistsException;
import com.hari.model.User;
import com.hari.repositories.UserRepository;
import com.hari.utils.ProjectUtils;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User registerUser(User user) {
		try {
			return userRepository.save(ProjectUtils.userPasswordEncryptFunction.apply(user, bCryptPasswordEncoder));
		} catch (final Exception e) {
			throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists.");
		}
	}

	public User retriveUser(String username) {

		return userRepository.findByUsername(username);
	}
}
