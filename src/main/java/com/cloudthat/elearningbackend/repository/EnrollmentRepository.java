package com.cloudthat.elearningbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Course;
import com.cloudthat.elearningbackend.entity.Enrollment;
import com.cloudthat.elearningbackend.entity.User;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
	Enrollment findByStudentAndCourse(User student, Course course);
	List<Enrollment> findAllByCourse(Course course);
	List<Enrollment> findAllByStudent(User student);
}
