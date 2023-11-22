package com.cloudthat.elearningbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.Technology;


@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {

}