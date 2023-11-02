package com.cloudthat.elearningbackend.service;

import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.entity.VerificationToken;
import com.cloudthat.elearningbackend.model.UserModel;
import com.cloudthat.elearningbackend.model.UserProfile;

public interface UserService {

	User registerUser(UserModel userModel);

	String validateVerificationToken(String token);

	VerificationToken generateNewVerificationToken(String oldToken);

	void saveVerificationTokenForUser(String token, User user);

	Boolean existsByEmail(String email);

	UserProfile getUserProfile(Long id);

	UserProfile updateUserProfile(Long id, UserProfile userProfile);

	void deleteUserProfile(Long id);

	
}
