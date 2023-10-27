package com.cloudthat.elearningbackend.model;

import com.cloudthat.elearningbackend.entity.Role;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginUserModel {
	@Email(message = "Enter valid email id")
	private String email;
	private String password;
	private Role role;
}
