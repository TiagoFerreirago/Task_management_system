package com.th.nextdone.security.accountcredentials;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	public AccountCredentialsDto() {}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, userName);
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
		return Objects.equals(password, other.password) && Objects.equals(userName, other.userName);
	}
	
	
}
