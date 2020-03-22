package com.hari.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Username connot be blank")
	private String username;
	@NotBlank(message = "Password cannot be blank")
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
