package com.th.nextdone.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.th.nextdone.security.accountcredentials.AccountCredentialsDto;
import com.th.nextdone.security.accountcredentials.TokenDto;
import com.th.nextdone.security.jwt.JwtTokenProvider;
import com.th.nextdone.security.repository.UserRepository;

@Service
public class AuthService {

	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private UserRepository userRepository;
	
	public ResponseEntity<TokenDto>signIn(AccountCredentialsDto credentials){
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), credentials.getPassword()));
		
		var user = userRepository.findByName(credentials.getUsername());
		
		if(user == null) {
			throw new UsernameNotFoundException("Username "+credentials.getUsername()+" not found!");
		}
		
		var token = tokenProvider.createAccessToken(user.getUsername(), user.getRoles());
		
		return ResponseEntity.ok().body(token);
	}
	
	public ResponseEntity<TokenDto> refreshToken(String username, String refreshToken) {
	    var user = userRepository.findByName(username);

	    if (user == null) {
	        throw new UsernameNotFoundException("Username " + username + " not found!");
	    }

	    TokenDto dto = tokenProvider.refreshAcessToken(refreshToken);
	    return ResponseEntity.ok(dto);
	}
	
}
