package org.dnyanyog.service;

import org.dnyanyog.dto.JwtResponse;
import org.dnyanyog.dto.LoginRequest;
import org.dnyanyog.dto.SignupRequest;
import org.dnyanyog.entity.User;
import org.dnyanyog.exception.UserAlreadyExistsException;
import org.dnyanyog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
					loginRequest.getUsernameOrEmail(),
					loginRequest.getPassword()
			)	
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		
		String jwt = jwtService.generateToken(user);
		
		return new JwtResponse(
			jwt,
			"Bearer",
			user.getId(),
			user.getUsername(),
			user.getEmail(),
			user.getFullName(),
			user.getRole()
		);
	};
	
	public User registerUser(SignupRequest signupRequest) {
		// Check if username exists
		if(userRepository.existsByUsername(signupRequest.getUsername())) {
			throw new UserAlreadyExistsException("Username is already taken!");
		}
		
		// Check if email exists
		if(userRepository.existsByEmail(signupRequest.getEmail())) {
			throw new UserAlreadyExistsException("Email is already taken!");
		}
		
		// Create new user
		User user = new User();
		user.setUsername(signupRequest.getUsername());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		user.setFullName(signupRequest.getFullName());
		user.setRole("USER");
		user.setEnabled(true);
		
		return userRepository.save(user);
	}
}
