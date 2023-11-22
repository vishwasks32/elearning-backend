package com.cloudthat.elearningbackend.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.TrueFalseConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProgress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
	private User student;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
	private Course course;
    @ManyToOne(cascade = CascadeType.ALL)
	private Lesson lastAccessedLesson;
	@Convert(converter = TrueFalseConverter.class)
	private Boolean completionStatus = false;
	@CreationTimestamp
	private Instant createdAt;
	@UpdateTimestamp
	private Instant modifiedAt;

}
