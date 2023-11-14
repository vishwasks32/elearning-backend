package com.cloudthat.elearningbackend.exceptions;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import com.cloudthat.elearningbackend.model.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, message);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MissingAuthorizationHeaderException.class)
	public ResponseEntity<ErrorResponse> MissingAuthorizationHeaderException(MissingAuthorizationHeaderException ex){
		String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, message);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> TokenExpiredException(TokenExpiredException ex){
		String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, message);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    	String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, message);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(ResourceAccessException.class)
	public ResponseEntity<ErrorResponse> handleRsourceAccessException(ResourceAccessException ex) {
    	String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, message);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.FORBIDDEN);
    }
	
	
    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
    	String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, message);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
		String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, message);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
	
	// fallback Handler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> customExceptionHandler(Exception ex){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

	    // converting the stack trace to String
	    StringWriter stringWriter = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(stringWriter);
	    ex.printStackTrace(printWriter);
	    String stackTrace = stringWriter.toString();

	        return new ResponseEntity<ErrorResponse>(
	            new ErrorResponse(
	              status, 
	              ex.getMessage(), 
	              stackTrace // specifying the stack trace in case of 500s
	            ),
	            status
	        );
	}
}
