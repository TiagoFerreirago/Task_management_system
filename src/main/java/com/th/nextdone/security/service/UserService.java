package com.th.nextdone.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.th.nextdone.security.model.User;
import com.th.nextdone.security.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
  
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByName(username);
		if(user != null) {
			return user;
		}
		else {
			throw new UsernameNotFoundException("Username "+username+" not found!");
		}
		
	}
	
	
}
