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
public class UserRequest {
	private String email;
	private Long id;
	private Role role;
}
