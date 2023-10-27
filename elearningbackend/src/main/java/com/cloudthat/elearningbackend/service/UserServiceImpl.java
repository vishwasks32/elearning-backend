package com.cloudthat.elearningbackend.service;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cloudthat.elearningbackend.entity.CustomUserDetails;
import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.entity.VerificationToken;
import com.cloudthat.elearningbackend.model.UserModel;
import com.cloudthat.elearningbackend.repository.UserRepository;
import com.cloudthat.elearningbackend.repository.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User registerUser(UserModel userModel) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setEmailId(userModel.getEmail());
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setRole(userModel.getRole());
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		
		try {
			userRepository.save(user);
			return user;
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityViolationException(null);
		}
		
	}

	@Override
	public void saveVerificationTokenForUser(String token, User user) {
		// TODO Auto-generated method stub\
		VerificationToken verificationToken = new VerificationToken(token,user);
		verificationTokenRepository.save(verificationToken);
		
	}

	@Override
	public String validateVerificationToken(String token) {
		// TODO Auto-generated method stub
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		if(verificationToken == null) {
			return "invalid";
		}
		
		User user = verificationToken.getUser();
		Calendar calendar = Calendar.getInstance();
		
		if(verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <=0) {
			return "expired";
		}
		
		user.setIsActive(true);
		userRepository.save(user);
		
		return "valid";
	}

	@Override
	public VerificationToken generateNewVerificationToken(String oldToken) {
		// TODO Auto-generated method stub
		VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
		verificationToken.setToken(UUID.randomUUID().toString());
		verificationTokenRepository.save(verificationToken);
		return verificationToken;
	}

	@Override
	public Boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmailId(email);
	}

}
