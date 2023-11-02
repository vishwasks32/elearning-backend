package com.cloudthat.elearningbackend.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudthat.elearningbackend.model.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ErrorController extends BasicErrorController {
	
	public ErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
		super(errorAttributes, serverProperties.getError());
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/error")
	public ResponseEntity<ErrorResponse> registeruser(HttpServletRequest request){
		// add check for email exists in DB
		log.info("Error Occured");
		String message = request.toString();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, message);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
}
