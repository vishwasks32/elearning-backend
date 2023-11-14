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
public class ContentModel {
	private long contentId;
	private String contentTitle;
	private String contentUrl;
	private String contentDescription;
	private int contentOrder;
	@NotNull
	private Long lessonId;
	private String contentType;
}
