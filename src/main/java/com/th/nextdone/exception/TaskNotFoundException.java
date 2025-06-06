package com.th.nextdone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public TaskNotFoundException(String msg) {
		super(msg);
	}
	
	public TaskNotFoundException() {
		super("No tasks found for the given date.");
	}
	
	public TaskNotFoundException(long id) {
		super("Task with ID " + id + " not found.");
	}
	

}
