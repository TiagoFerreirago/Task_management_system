package com.th.nextdone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CustomAccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CustomAccessDeniedException(String msg) {
		super(msg);
		
	}
	
	public CustomAccessDeniedException() {
		super("Only administrators can perform this action..");
	}
	
	

}
