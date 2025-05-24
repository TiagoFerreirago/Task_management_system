package com.th.nextdone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomIllegalArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomIllegalArgumentException() {
		super("Date cannot be null.");
	}
	
	public CustomIllegalArgumentException(String msg) {
		super(msg);
	}
}
