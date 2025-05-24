package com.th.nextdone.security.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.th.nextdone.security.accountcredentials.AccountCredentialsDto;
import com.th.nextdone.security.accountcredentials.TokenDto;
import com.th.nextdone.security.service.AuthService;



@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/signin")
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsDto credentials){
		
		if(credentialsAreInvalid(credentials)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client request"
);
		}
		ResponseEntity<TokenDto> token = authService.signIn(credentials);
		
		
		return token;
	}
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity<?> refreshToken(@PathVariable("username")String username, @RequestHeader("Authorization")String refreshToken){
		
		if(paramenterAreInvalid(username, refreshToken)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client request"
);
		}
		ResponseEntity<TokenDto> dto = authService.refreshToken(username, refreshToken);
		return dto;
	}

	@PostMapping(value = "/createacess")
	public AccountCredentialsDto createAcess(@RequestBody AccountCredentialsDto credentials) {
				
		return authService.createAccess(credentials);
	}
	
	
	   private static boolean credentialsAreInvalid(AccountCredentialsDto credentials) {
	        return credentials == null || StringUtils.isBlank(credentials.getUsername()) || StringUtils.isBlank(credentials.getPassword());
	    }
	   
	   private static boolean paramenterAreInvalid(String username, String refreshToken) {
		   return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
	   }
}
