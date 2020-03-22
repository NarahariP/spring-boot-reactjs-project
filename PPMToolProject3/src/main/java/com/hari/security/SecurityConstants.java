package com.hari.security;

public class SecurityConstants {
	public static final String SIGN_UP_URLS = "/users/register/**";
	public static final String LOGIN_URL = "/users/login";
	public static final String H2_DB_URL = "h2-console/**";
	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final long TOKEN_EXPIRATION_TIME = 300_000;
}
