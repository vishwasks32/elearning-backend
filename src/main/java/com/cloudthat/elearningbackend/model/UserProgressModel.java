package com.cloudthat.elearningbackend.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProgressModel {
	@NotNull
	private Long studentId;
	@NotNull
	private Long courseId;
	@NotNull
	private Long lastAccessedLesson;
	@NotNull
	private Boolean completionStatus;
}
