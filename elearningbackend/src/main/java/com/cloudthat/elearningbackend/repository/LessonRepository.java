package com.cloudthat.elearningbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Course;
import com.cloudthat.elearningbackend.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

	List<Lesson> findAllByCourse(Course course);


}
