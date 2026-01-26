package org.dnyanyog.service;

import org.dnyanyog.entity.User;
import org.dnyanyog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameorEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(usernameorEmail, usernameorEmail)
				.orElseThrow(() -> new UsernameNotFoundException(
					"User not found with username or email: " + usernameorEmail));
		
		return user;
	}
}
