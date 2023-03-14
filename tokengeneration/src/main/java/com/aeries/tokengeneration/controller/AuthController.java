package com.aeries.tokengeneration.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthController {
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		// Check user credentials
		// Generate token
		String token = Jwts.builder().setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 86400000))
				.signWith(SignatureAlgorithm.HS512, "mySecretKey").compact();
		// Return token
		return ResponseEntity.ok(token);
	}

}
