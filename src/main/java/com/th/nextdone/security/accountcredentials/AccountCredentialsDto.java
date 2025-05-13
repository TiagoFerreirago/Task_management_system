package com.th.nextdone.security.accountcredentials;

import java.io.Serializable;
import java.util.Objects;

import com.th.nextdone.security.model.User;

public class AccountCredentialsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String fullName;
	private String password;
	
	public AccountCredentialsDto() {}
	
	public AccountCredentialsDto(User user) {
		this.username = user.getUsername();
		this.fullName = user.getFullName();
		this.password = user.getPassword();
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

	@Override
	public int hashCode() {
		return Objects.hash(fullName, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountCredentialsDto other = (AccountCredentialsDto) obj;
		return Objects.equals(fullName, other.fullName) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	
	
}
