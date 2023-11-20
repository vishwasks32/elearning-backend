package com.cloudthat.elearningbackend.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizModel {
	private Long id;
	private String name;
	private String description;
	private Integer duration;
//	private List<QuestionModel> questions;
}
