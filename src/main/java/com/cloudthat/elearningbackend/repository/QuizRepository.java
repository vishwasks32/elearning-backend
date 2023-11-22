package com.cloudthat.elearningbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
