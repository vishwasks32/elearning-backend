package com.cloudthat.elearningbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Question;
import com.cloudthat.elearningbackend.entity.Quiz;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findAllByQuiz(Quiz quiz);

}
