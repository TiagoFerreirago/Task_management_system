package com.th.nextdone.security.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.th.nextdone.security.accountcredentials.AccountCredentialsDto;
import com.th.nextdone.security.accountcredentials.TokenDto;
import com.th.nextdone.security.service.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/signin")
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsDto credentials){
		
		if(credentialsIsInvalid(credentials)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid cliente request!");
		}
		ResponseEntity<TokenDto> token = authService.signIn(credentials);
		
		
		return ResponseEntity.ok().body(token);
	}
	
	   private static boolean credentialsIsInvalid(AccountCredentialsDto credentials) {
	        return credentials == null || StringUtils.isBlank(credentials.getUsername()) || StringUtils.isBlank(credentials.getPassword());
	    }
}
