package org.dnyanyog.controller;

import org.dnyanyog.dto.ApiResponse;
import org.dnyanyog.dto.JwtResponse;
import org.dnyanyog.dto.LoginRequest;
import org.dnyanyog.dto.SignupRequest;
import org.dnyanyog.entity.User;
import org.dnyanyog.service.AuthService;
import org.dnyanyog.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@PostMapping("/auth/login")
	public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
		return ResponseEntity.ok(ApiResponse.success("Login successful", jwtResponse));
	}
	
	@PostMapping("/auth/signup")
	public ResponseEntity<ApiResponse> registerUesr(@Valid @RequestBody SignupRequest signupRequest){
		User user = authService.registerUser(signupRequest);
		return ResponseEntity.ok(ApiResponse.success("User registered successfully", user));
	}
	
	@GetMapping("/check-username/{username}")
	public ResponseEntity<ApiResponse> checkUsernameAvailibility(@PathVariable String username){
		// This would typically check if username exists
		UserDetails user = userDetailsService.loadUserByUsername(username);
		System.out.println("*********USER:- "+ user + " for username:-" + username);
		return ResponseEntity.ok(ApiResponse.success("Username check completed", user));
	}
	
	@GetMapping("/check-email/{email}")
	public ResponseEntity<ApiResponse> checkEmailAvailibility(@PathVariable String email){
		return ResponseEntity.ok(ApiResponse.success("Email check completed", null));
	}
}
