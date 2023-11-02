package com.cloudthat.elearningbackend.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudthat.elearningbackend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmailId(String email);
	Boolean existsByEmailId(String email);
}
