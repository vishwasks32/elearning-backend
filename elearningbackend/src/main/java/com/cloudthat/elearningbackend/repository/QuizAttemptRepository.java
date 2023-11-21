package com.cloudthat.elearningbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Quiz;
import com.cloudthat.elearningbackend.entity.QuizAttempt;
import com.cloudthat.elearningbackend.entity.User;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

	List<QuizAttempt> findAllByQuiz(Quiz quiz);

	QuizAttempt findByStudentAndQuiz(User student, Quiz quiz);

}
