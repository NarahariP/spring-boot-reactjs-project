package com.hari.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hari.model.User;
import com.hari.service.CustomUserDetailService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			final String jwt = getJwtTokenFromRequest(request);
			if (StringUtils.hasText(jwt) && jwtTokenProvider.valdiateToken(jwt)) {
				final Long userId = jwtTokenProvider.retriveUserIdFromJwtToken(jwt);
				final User userDetails = customUserDetailService.loadUserById(userId);
				final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, Collections.emptyList());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (final Exception e) {
			logger.error("Unable to set User Authentication to Security Context " + e);
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtTokenFromRequest(HttpServletRequest request) {
		final String barerToken = request.getHeader(SecurityConstants.HEADER_STRING);
		if (StringUtils.hasText(barerToken) && barerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			return barerToken.substring(7, barerToken.length());
		}
		return null;
	}

}
