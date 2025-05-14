package com.th.nextdone.security.accountcredentials;

import java.io.Serializable;
import com.th.nextdone.security.model.User;

public class AccountCredentialsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String fullName;
	private String password;
	private String email;
	
	public AccountCredentialsDto() {}
	
	public AccountCredentialsDto(User user) {
		this.username = user.getUsername();
		this.fullName = user.getFullName();
		this.email = user.getEmail();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
