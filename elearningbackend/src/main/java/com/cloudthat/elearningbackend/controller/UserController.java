package com.cloudthat.elearningbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.entity.VerificationToken;
import com.cloudthat.elearningbackend.event.RegistrationCompleteEvent;
import com.cloudthat.elearningbackend.model.JwtRequest;
import com.cloudthat.elearningbackend.model.JwtResponse;
import com.cloudthat.elearningbackend.model.LoginUserModel;
import com.cloudthat.elearningbackend.model.UserModel;
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
	public ResponseEntity<String> registeruser(@RequestBody UserModel userModel, final HttpServletRequest request){
		// add check for email exists in DB
        if(userService.existsByEmail(userModel.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
		try {
			User user = userService.registerUser(userModel);
			publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
			return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
		} catch (DataIntegrityViolationException  e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already taken.");
		}
	}
	
    @PostMapping("/login")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        try {
        	authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
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
