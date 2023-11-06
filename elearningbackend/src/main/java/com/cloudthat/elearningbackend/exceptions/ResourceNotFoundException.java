package com.cloudthat.elearningbackend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resourceName;
	private String fieldName;
	private Long fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
		super(String.format("%s not found with %s: %s", resourceName,fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ResourceNotFoundException(String resourceName2, String fieldName2, String email) {
		// TODO Auto-generated constructor stub
		super(String.format("%s not found with %s: %s", resourceName2,fieldName2, email));
		this.resourceName = resourceName2;
		this.fieldName = fieldName2;
	}
}
