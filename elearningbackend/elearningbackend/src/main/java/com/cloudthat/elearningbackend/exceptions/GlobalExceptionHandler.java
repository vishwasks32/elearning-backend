package com.cloudthat.elearningbackend.exceptions;


import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
	
	
    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
    	String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, message);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
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
