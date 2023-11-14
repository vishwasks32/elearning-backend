package com.cloudthat.elearningbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Content;
import com.cloudthat.elearningbackend.entity.Lesson;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	List<Content> findAllByLesson(Lesson lesson);

}
