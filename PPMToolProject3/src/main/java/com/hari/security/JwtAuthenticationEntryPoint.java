package com.hari.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hari.exception.InValidLoginResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		final InValidLoginResponse loginResponse = new InValidLoginResponse();
		final String jsonLoginResponse = new Gson().toJson(loginResponse);
		response.setContentType("application/json");
		response.setStatus(401);
		response.getWriter().print(jsonLoginResponse);

	}

}
