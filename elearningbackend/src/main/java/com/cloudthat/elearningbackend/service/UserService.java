package com.cloudthat.elearningbackend.service;

import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.entity.VerificationToken;
import com.cloudthat.elearningbackend.model.UserModel;

public interface UserService {

	User registerUser(UserModel userModel);

	String validateVerificationToken(String token);

	VerificationToken generateNewVerificationToken(String oldToken);

	void saveVerificationTokenForUser(String token, User user);

	Boolean existsByEmail(String email);

	
}
