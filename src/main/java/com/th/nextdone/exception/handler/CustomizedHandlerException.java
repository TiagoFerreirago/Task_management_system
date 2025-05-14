package com.th.nextdone.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.th.nextdone.exception.InvalidDataException;
import com.th.nextdone.exception.TaskNotFoundException;

@RestController
@ControllerAdvice
public class CustomizedHandlerException extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleGenericException(Exception ex, WebRequest http){
		
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), http.getDescription(false), new Date());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(TaskNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handlerTaskNotFoundException(Exception ex, WebRequest http){
		
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), http.getDescription(false), new Date());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidDataException.class)
	public final ResponseEntity<ExceptionResponse> handlerInvalidDataException(Exception ex, WebRequest http){
		
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), http.getDescription(false), new Date());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<ExceptionResponse> handleIllegalArgumentException(Exception ex, WebRequest http){
		
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), http.getDescription(false), new Date());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(Exception ex, WebRequest http){
		
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), http.getDescription(false), new Date());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ExceptionResponse> handleAccessDeniedException(Exception ex, WebRequest http){
		
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), http.getDescription(false), new Date());
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}
	
	
}
