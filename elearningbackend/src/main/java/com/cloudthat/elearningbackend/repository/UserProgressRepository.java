package com.cloudthat.elearningbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Course;
import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.entity.UserProgress;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
	UserProgress findByStudentAndCourse(User student, Course course);
	List<UserProgress> findAllByCourse(Course course);
	List<UserProgress> findAllByStudent(User user);
}
