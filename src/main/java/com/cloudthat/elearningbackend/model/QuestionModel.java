package com.cloudthat.elearningbackend.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionModel {
	private Long id;
	private String text;
	private String image;
	private List<OptionModel> options;
	private Long quizId;
}
