package com.polling.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

//Generates JWT after successful login to allow user to access restricted areas/content.
@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	// Following JWT Values listed in application.properties
	@Value("${app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;
	
	public String generateToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
		
		return Jwts.builder()
				.setSubject(Long.toString(userPrincipal.getId()))
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(token)
			.getBody();
		
		return Long.parseLong(claims.getSubject());
	}
	
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT Signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT Token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT Token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT Token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
