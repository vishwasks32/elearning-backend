package com.cloudthat.elearningbackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
