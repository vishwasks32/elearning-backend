package com.cloudthat.elearningbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.entity.VerificationToken;
import com.cloudthat.elearningbackend.event.RegistrationCompleteEvent;
import com.cloudthat.elearningbackend.model.ApiResponse;
import com.cloudthat.elearningbackend.model.JwtRequest;
import com.cloudthat.elearningbackend.model.JwtResponse;
import com.cloudthat.elearningbackend.model.UserModel;
import com.cloudthat.elearningbackend.model.UserProfile;
import com.cloudthat.elearningbackend.model.UserRequest;
import com.cloudthat.elearningbackend.service.CustomUserDetailsService;
import com.cloudthat.elearningbackend.service.UserService;
import com.cloudthat.elearningbackend.utility.JWTUtility;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/api/v1/user/profile")
	public ResponseEntity<ApiResponse> getUserProfile(@RequestBody UserRequest userRequest){
			UserProfile userProfile = userService.getUserProfile(userRequest.getEmail());
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User Fetched Successfully", userProfile), HttpStatus.OK);
	}
	
	
	@PutMapping("/api/v1/user/profile/{id}")
	public ResponseEntity<ApiResponse> updateUserProfile(@PathVariable("id") Long id, @RequestBody UserProfile userProfile){
		UserProfile updatedUser = userService.updateUserProfile(id, userProfile);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User Updated Successfully", updatedUser), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/api/v1/user/profile/{id}")
	public ResponseEntity<ApiResponse> deleteUserProfile(@PathVariable("id") Long id){
		userService.deleteUserProfile(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}
	
	
}
