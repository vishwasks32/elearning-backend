package com.cloudthat.elearningbackend.service;

import java.util.List;

import com.cloudthat.elearningbackend.model.CourseDetails;
import com.cloudthat.elearningbackend.model.CourseModel;
import com.cloudthat.elearningbackend.model.EnrollmentModel;
import com.cloudthat.elearningbackend.model.StudentModel;

public interface CourseService {

	CourseDetails registerCourse(CourseModel courseModel);

	List<CourseDetails> getAllCourses();

	CourseDetails getCourseDetails(Long courseId);

	CourseDetails updateCourseDetails(Long courseId, CourseModel courseModel);

	void disableCourse(Long courseId);

	EnrollmentModel enrollCourse(Long courseId, Long studentId);

	List<StudentModel> getStudentsForCourse(Long courseId);

	List<CourseDetails> getCoursesForStudent(Long userId);

	List<CourseDetails> getCoursesByTechnology(Long techId);

}
