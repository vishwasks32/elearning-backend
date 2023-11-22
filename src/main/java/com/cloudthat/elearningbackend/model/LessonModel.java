package com.cloudthat.elearningbackend.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonModel {
	private Long lessonId;
	private Long courseId;
	@NotBlank
	private String lessonTitle;
	@NotNull
	private int lessonOrder;
	private List<ContentModel> courseContents;
}
