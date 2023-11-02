package com.cloudthat.elearningbackend.model;

import com.cloudthat.elearningbackend.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {
	@NotBlank(message = "First Name is required")
	private String firstName;
	private String lastName;
	@Email(message = "Enter valid email id")
	private String email;
	private String password;
	private String profilePicture;
	private String phoneNumber;
	private Role role;
}
