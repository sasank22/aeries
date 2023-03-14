package com.aeries.tokengeneration.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenValidationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getTokenFromRequest(request);
		if (token != null && validateToken(token)) {
			// Token is valid, proceed with the request
			filterChain.doFilter(request, response);
		} else {
			// Token is not valid, assign a new token
			String newToken = generateNewToken();
			// Add the new token to the response header
			response.setHeader("Authorization", newToken);
			// Proceed with the request
			filterChain.doFilter(request, response);
		}
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}

	private boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey("mySecretKey").parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	private String generateNewToken() {
		// Generate a new token using the same logic as the login endpoint
		String token = Jwts.builder().setSubject("username")
				.setExpiration(new Date(System.currentTimeMillis() + 86400000))
				.signWith(SignatureAlgorithm.HS512, "mySecretKey").compact();
		return token;
	}
}
