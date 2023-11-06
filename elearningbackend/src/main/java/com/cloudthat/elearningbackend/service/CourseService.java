package com.cloudthat.elearningbackend.service;

import java.util.List;

import com.cloudthat.elearningbackend.model.CourseDetails;
import com.cloudthat.elearningbackend.model.CourseModel;

public interface CourseService {

	CourseDetails registerCourse(CourseModel courseModel);

	List<CourseDetails> getAllCourses();

	CourseDetails getCourseDetails(Long courseId);

	CourseDetails updateCourseDetails(Long courseId, CourseModel courseModel);

	void disableCourse(Long courseId);

}
