package com.cloudthat.elearningbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Options;
import com.cloudthat.elearningbackend.entity.Question;

@Repository
public interface OptionRepository extends JpaRepository<Options, Long> {

	List<Options> findAllByQuestion(Question question);

}
