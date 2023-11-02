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
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager  authenticationProvider;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping("register")
	public ResponseEntity<ApiResponse> registeruser(@RequestBody UserModel userModel, final HttpServletRequest request){
		// add check for email exists in DB
        if(userService.existsByEmail(userModel.getEmail())){
            return new ResponseEntity<ApiResponse>(new ApiResponse("Email is already taken!", false), HttpStatus.BAD_REQUEST);
        }
		User user = userService.registerUser(userModel);
		publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return new ResponseEntity<ApiResponse>(new ApiResponse("User created successfully", true), HttpStatus.CREATED);
	}
	
    @PostMapping("login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{
    	UsernamePasswordAuthenticationToken unauthenticatedToken = UsernamePasswordAuthenticationToken.unauthenticated(
    			jwtRequest.getUsername(), jwtRequest.getPassword());
        try {
        	authenticationProvider.authenticate(
        			unauthenticatedToken
            );
        } catch (BadCredentialsException e) {
        	return new ResponseEntity<JwtResponse>(new JwtResponse(null,e.getMessage(),false),HttpStatus.UNAUTHORIZED);
        }catch(NullPointerException ex) {
        	return new ResponseEntity<JwtResponse>(new JwtResponse(null,"User Name Not Found",false),HttpStatus.UNAUTHORIZED);
        }catch(DisabledException ex) {
        	return new ResponseEntity<JwtResponse>(new JwtResponse(null,"User Account is disabled",false),HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails
                = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return new ResponseEntity<JwtResponse>(new JwtResponse(token,"Token generated Successfully",true),HttpStatus.OK);
    }
	
	
	@GetMapping("verifyRegistration")
	public String verifyRegistration(@RequestParam("token") String token) {
		String result = userService.validateVerificationToken(token);
		if(result.equalsIgnoreCase("valid")) {
			return "User verified Succesfully";
		}
		return "Bad user";
		
	}
	
	@GetMapping("resendVerifytoken")
	public String resendVerificaionToken(@RequestParam("token") String oldToken, HttpServletRequest httpServletRequest) {
		VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
		User user = verificationToken.getUser();
		resendVerificationTokenMail(user,applicationUrl(httpServletRequest), verificationToken);
		return "Verification link Sent";
	}
	
	
	@GetMapping("/api/v1/user/profile")
	public ResponseEntity<ApiResponse> getUserProfile(@RequestBody UserRequest userRequest){
			UserProfile userProfile = userService.getUserProfile(userRequest.getId());
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
	
	private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
		// TODO Auto-generated method stub
		String url = applicationUrl+ "/verifyRegistration?token="+verificationToken.getToken();
		// just mimicking email sending here
		log.info("URL link to verify: {}",url);
	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
}
