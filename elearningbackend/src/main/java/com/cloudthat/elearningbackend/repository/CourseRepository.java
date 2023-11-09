package com.cloudthat.elearningbackend.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Course;
import com.cloudthat.elearningbackend.entity.Technology;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findAllByTechnology(Technology technology);



}
