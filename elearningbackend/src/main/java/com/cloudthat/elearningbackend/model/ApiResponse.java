package com.cloudthat.elearningbackend.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApiResponse {
	private boolean success;
    private String message;
    private Object data;
	public ApiResponse(String message, boolean success) {
		super();
		this.success = success;
		this.message = message;
	}
    
    
}
