package com.cloudthat.elearningbackend.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseDetails {
	private Long id;
	private String courseName;
	private String courseDescription;
	private String courseImage;
	private Date startDate;
	private Date endDate;
	private Long facultyId;
	private Long technologyId;
}
