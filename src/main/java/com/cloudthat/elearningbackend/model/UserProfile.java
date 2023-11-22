package com.cloudthat.elearningbackend.model;


import com.cloudthat.elearningbackend.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {
	//Fields Read Only
	private Long id;
	private String firstName;
	private String email;
	private Role role;
	
	// fields allowed to update
	private String lastName;
	private String profilePicture;
	private String phoneNumber;
}
