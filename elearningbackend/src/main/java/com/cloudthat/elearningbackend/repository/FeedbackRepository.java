package com.cloudthat.elearningbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudthat.elearningbackend.entity.Course;
import com.cloudthat.elearningbackend.entity.Feedback;
import com.cloudthat.elearningbackend.entity.User;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	List<Feedback> findByCourse(Course course);

	List<Feedback> findByFaculty(User faculty);

	List<Feedback> findByStudent(User student);

	Feedback findByStudentAndCourse(User student, Course course);

}
