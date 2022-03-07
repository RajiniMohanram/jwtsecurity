package com.cts.proj.app.auth;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -1598332378374421071L;

	public static final long TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

	@Value("${jwt.code}")
	private String code;

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(code).parseClaimsJws(token).getBody();
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		long currentTime = System.currentTimeMillis();
		Date issueDateTime = new Date(currentTime);
		Date expiryDateTime = new Date(currentTime + TOKEN_VALIDITY);

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(issueDateTime)
				.setExpiration(expiryDateTime)
				.signWith(SignatureAlgorithm.HS512, code)
				.compact();
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (
				username.equals(userDetails.getUsername()) 
				&& !isTokenExpired(token)
				);
	}
}
