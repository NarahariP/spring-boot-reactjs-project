package com.hari.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.hari.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);

	public String generateToken(Authentication authentication) {
		final User user = (User) authentication.getPrincipal();
		final Date now = new Date();
		final Date expiryDate = new Date(now.getTime() + SecurityConstants.TOKEN_EXPIRATION_TIME);
		final String userId = Long.toString(user.getId());
		final Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("id", Long.toString(user.getId()));
		claimsMap.put("username", user.getUsername());
		claimsMap.put("fullName", user.getFullName());
		return Jwts.builder().setSubject(userId).setClaims(claimsMap).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET).compact();

	}

	// Validate Token
	public boolean valdiateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
			return true;
		} catch (final SignatureException e) {
			LOG.error("Invalid JWT Signature");
		} catch (final MalformedJwtException e) {
			LOG.error("Invalid JWT Token");
		} catch (final ExpiredJwtException e) {
			LOG.error("Expired JWT Token");
		} catch (final UnsupportedJwtException e) {
			LOG.error("Unsupported JWt Token");
		} catch (final IllegalArgumentException e) {
			LOG.error("JWT Token String is Empty");
		}
		return false;
	}

	// Read user name from Token
	public Long retriveUserIdFromJwtToken(String token) {
		final Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
		final String id = (String) claims.get("id");
		return Long.parseLong(id);
	}
}
