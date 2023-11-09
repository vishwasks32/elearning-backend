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
public class EnrollmentModel {
	private Long enrollmentId;
	private Long courseId;
	private Long studentId;
	private Role role;
}
