package com.cloudthat.elearningbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OptionModel {
	private Long id;
	private String text;
	private String image;
	private Long questionId;
	private Boolean isCorrect;;
}
