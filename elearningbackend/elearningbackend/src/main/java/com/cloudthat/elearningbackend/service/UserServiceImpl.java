package com.cloudthat.elearningbackend.service;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.DisabledException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.entity.VerificationToken;
import com.cloudthat.elearningbackend.exceptions.ResourceNotFoundException;
import com.cloudthat.elearningbackend.model.UserModel;
import com.cloudthat.elearningbackend.model.UserProfile;
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

	@Override
	public UserProfile getUserProfile(Long id) {
		UserProfile userProfile;
		try {
			// TODO Auto-generated method stub
			User user = userRepository.findById(id).get();
			
			if(!user.getIsActive()) {
				throw new DisabledException("User account is Disabled");
			}
			userProfile = new UserProfile();
			userProfile.setEmail(user.getEmailId());
			userProfile.setFirstName(user.getFirstName());
			userProfile.setLastName(user.getLastName());
			userProfile.setPhoneNumber(user.getPhoneNumber());
			userProfile.setProfilePicture(user.getProfilePicture());
			userProfile.setRole(user.getRole());
		} catch (Exception e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("User","Id", id);
		}
		return userProfile;
	}

	@Override
	public UserProfile updateUserProfile(Long id, UserProfile userProfile) {
		// TODO Auto-generated method stub
		User userDB;
		try {
			userDB = userRepository.findById(id).get();
			if (!userDB.getIsActive()) {
				throw new DisabledException("User account is Disabled");
			}
			// Note: we will not update Email Id, first Name, Role
			if (Objects.nonNull(userProfile.getLastName()) && !"".equalsIgnoreCase(userProfile.getLastName())) {
				userDB.setLastName(userProfile.getLastName());
			}
			if (Objects.nonNull(userProfile.getProfilePicture())
					&& !"".equalsIgnoreCase(userProfile.getProfilePicture())) {
				userDB.setProfilePicture(userProfile.getProfilePicture());
			}
			if (Objects.nonNull(userProfile.getPhoneNumber()) && !"".equalsIgnoreCase(userProfile.getPhoneNumber())) {
				userDB.setPhoneNumber(userProfile.getPhoneNumber());
			}
			
			userRepository.save(userDB);
			
			userProfile = new UserProfile();
			userProfile.setEmail(userDB.getEmailId());
			userProfile.setFirstName(userDB.getFirstName());
			userProfile.setLastName(userDB.getLastName());
			userProfile.setPhoneNumber(userDB.getPhoneNumber());
			userProfile.setProfilePicture(userDB.getProfilePicture());
			userProfile.setRole(userDB.getRole());
			
		} catch(NullPointerException ex){
			throw new ResourceNotFoundException("User","id", id);
		}catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityViolationException(null);
		}
		
		return userProfile;
		
	}

	@Override
	public void deleteUserProfile(Long id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				User userDB;
				try {
					userDB = userRepository.findById(id).get();
					if (!userDB.getIsActive()) {
						throw new DisabledException("User account is Disabled");
					}
					userDB.setIsActive(false);
					
					userRepository.save(userDB);
				} catch(NullPointerException ex){
					throw new ResourceNotFoundException("User","id", id);
				}catch (DataIntegrityViolationException e) {
					// TODO: handle exception
					throw new DataIntegrityViolationException(null);
				}
	}

}
