package com.cloudthat.elearningbackend.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizAttemptModel {
	private Long id;
	private Long studentId;
	private Long quizId;
	private Instant startTime;
	private Instant endTime;
	private Integer score;
}
