package com.cloudthat.elearningbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizScoreModel {
	private Long id;
	private Long studentId;
	private Integer score;
}
