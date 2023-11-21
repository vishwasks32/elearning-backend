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
public class FeedbackModel {
	private Long id;
	@NotNull
	private Long courseId;
	@NotNull
	private Long studentId;
	private Long facultyId;
	private String content;
	@NotNull
	private Integer rating;
}
