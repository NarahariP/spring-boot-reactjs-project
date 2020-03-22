package com.hari.payload;

public class JWTLoginSuccessResponse {

	private boolean sucess;
	private String token;

	public JWTLoginSuccessResponse() {
		super();
	}

	public JWTLoginSuccessResponse(boolean sucess, String token) {
		this.sucess = sucess;
		this.token = token;
	}

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "JWTLoginSuccessResponse [sucess=" + sucess + ", token=" + token + "]";
	}

}
